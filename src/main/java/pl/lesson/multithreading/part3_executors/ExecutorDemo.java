package pl.lesson.multithreading.part3_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 3.1 Pula wątków i Future. 6 zadań, ale tylko 3 wątki (pool-1-thread-1..3).
 */
public class ExecutorDemo {

    public static void main(String[] args) throws Exception {
        try (ExecutorService pool = Executors.newFixedThreadPool(3)) {
            for (int i = 1; i <= 6; i++) {
                int id = i;
                pool.submit(() -> System.out.println(
                        "Zadanie " + id + " na " + Thread.currentThread().getName()));
            }

            Future<Integer> future = pool.submit(() -> {
                Thread.sleep(500);
                return 42;
            });
            System.out.println("Wynik: " + future.get()); // blokuje do wyniku
        } // try-with-resources (Java 19+) = shutdown + czekanie na zadania
    }
}
