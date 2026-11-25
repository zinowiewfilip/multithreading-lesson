# Wielowątkowość w Javie i Spring Boot – przykłady do zajęć (45 min)

Kod do zajęć: od zwykłych wątków, przez synchronizację i pule wątków, do `@Async` w Spring Boot.

**Wymagania:** Java 21+, Maven 3.9+. Spring Boot 3.5.

## Struktura

| Pakiet | Temat | Czas na zajęciach |
| --- | --- | --- |
| `part1_threads` | `Thread`, `Runnable`, `start` vs `run`, `join`, `interrupt`, stany wątku, demony | 15 min |
| `part2_race` | race condition, `volatile`, `synchronized`, `AtomicInteger`, `ReentrantLock`, `ConcurrentHashMap`, deadlock | 8 min |
| `part3_executors` | `ExecutorService`, `Future`, `Callable`, `CompletableFuture`, wątki wirtualne | 7 min |
| `part4_spring` | `@EnableAsync`, `ThreadPoolTaskExecutor`, `@Async`, singleton a współdzielony stan | 7 min |
| `exercise` | ćwiczenie: równoległa suma + rozwiązanie | 3–5 min |

## Uruchamianie przykładów z części 1–3

Każdy przykład to osobna klasa z `main`. Najprościej uruchomić ją z IDE albo przez Maven:

```bash
mvn -q compile exec:java -Dexec.mainClass=pl.lesson.multithreading.part1_threads.FirstThread
```

### Część 1 – zwykłe wątki

| Klasa | Co pokazuje |
| --- | --- |
| `FirstThread` | dwa sposoby tworzenia wątku; kolejność wypisów zmienia się między uruchomieniami |
| `StartVsRun` | `run()` działa w `main`, `start()` w nowym wątku; drugi `start()` rzuca wyjątek |
| `JoinDemo` | `sleep` i `join` – zakomentuj `join()` i zobacz różnicę |
| `InterruptDemo` | grzeczne zatrzymywanie wątku przez `interrupt()` |
| `ThreadStates` | stany `NEW`, `TIMED_WAITING`, `BLOCKED`, `TERMINATED` |
| `DaemonDemo` | wątek demon nie trzyma JVM przy życiu |
| `TooManyThreads` | koszt tworzenia 2000 wątków platformowych – wstęp do pul |

### Część 2 – race condition i synchronizacja

| Klasa | Co pokazuje |
| --- | --- |
| `RaceDemo` | `counter++` z dwóch wątków gubi inkrementacje (np. 165 792 zamiast 200 000) |
| `VolatileNotEnough` | `volatile` nie daje atomowości – wynik nadal zły |
| `VolatileVisibility` | do czego `volatile` służy: widoczność flagi między wątkami |
| `SynchronizedFix`, `AtomicFix`, `LockFix` | trzy poprawne rozwiązania – zawsze 200 000 |
| `ConcurrentMapDemo` | `HashMap` vs `ConcurrentHashMap` przy zapisie z wielu wątków |
| `DeadlockDemo` | bonus: zakleszczenie (program wisi, zakończ Ctrl+C; podejrzyj przez `jstack <pid>`) |

### Część 3 – pule wątków

| Klasa | Co pokazuje |
| --- | --- |
| `ExecutorDemo` | 6 zadań na 3 wątkach, `Future.get()` |
| `CallableAndInvokeAll` | `invokeAll`, czas = max zamiast sumy, timeout i `cancel` |
| `CompletableFutureDemo` | `supplyAsync`, `thenCombine`, `exceptionally` |
| `VirtualThreadsDemo` | 10 000 zadań po 1 s na wątkach wirtualnych: ~1 s; 1 000 zadań na puli 100 wątków: ~10 s |

## Część 4 – Spring Boot

Uruchom aplikację:

```bash
mvn spring-boot:run
```

| Endpoint | Co pokazuje |
| --- | --- |
| `GET /thread` | w jakim wątku Tomcat obsłużył żądanie (`http-nio-8080-exec-N` albo wirtualny) |
| `GET /reports` | trzy raporty `@Async` równolegle: ~2 s zamiast ~6 s; w logach wątki `async-1..3` |
| `GET /reports/wrong` | pułapka self-invocation: `this.generate()` omija proxy, wychodzi ~6 s |
| `POST /email?to=` | `@Async void` – odpowiedź od razu; pusty `to` pokazuje obsługę wyjątku w logach |
| `POST /counter`, `GET /counter` | race condition na polu beana-singletona |

### Demo: race condition w beanie

```bash
curl -X POST localhost:8080/counter/reset
seq 1000 | xargs -P 50 -I{} curl -s -X POST localhost:8080/counter
curl localhost:8080/counter
# {"unsafe": 987412, "safe": 1000000}   <- "unsafe" za każdym razem inny
```

### Demo: wątki wirtualne

W `application.properties` ustaw `spring.threads.virtual.enabled=true`, zrestartuj i wywołaj `GET /thread` – pole `virtual` zmieni się na `true`.

## Ćwiczenie

Otwórz `exercise/ParallelSum.java` i zaimplementuj `sum(n, threads)`. Potem usuń `@Disabled` w `ParallelSumTest` i uruchom:

```bash
mvn test -Dtest=ParallelSumTest
```

Rozwiązanie (dwie wersje: `Thread` + `join` oraz `ExecutorService`) jest w `ParallelSumSolution`.

## Pytania kontrolne

1. Czym różni się `start()` od `run()`?
2. Dlaczego `counter++` nie jest bezpieczne wątkowo?
3. Co daje `volatile`, a czego nie daje?
4. Dlaczego `@Async` wywołane z tej samej klasy nie działa asynchronicznie?
5. Kiedy wątki wirtualne pomogą, a kiedy nie?
