package pl.lesson.multithreading.part3_executors;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Callable zwraca wynik i może rzucić wyjątek. invokeAll uruchamia listę zadań i czeka na wszystkie.
 * get(timeout) chroni przed wiecznym czekaniem.
 */
public class CallableAndInvokeAll {

    public static void main(String[] args) throws Exception {
        try (ExecutorService pool = Executors.newFixedThreadPool(3)) {
            List<Callable<String>> tasks = List.of(
                    () -> slow("A", 300),
                    () -> slow("B", 600),
                    () -> slow("C", 100));

            long start = System.currentTimeMillis();
            for (Future<String> f : pool.invokeAll(tasks)) {
                System.out.println(f.get());
            }
            System.out.println("Razem: " + (System.currentTimeMillis() - start) + " ms (max, nie suma)");

            Future<String> tooSlow = pool.submit(() -> slow("D", 2000));
            try {
                tooSlow.get(500, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                System.out.println("D przekroczyło 500 ms – anuluję");
                tooSlow.cancel(true); // wysyła interrupt do wątku
            }
        }
    }

    static String slow(String name, long ms) throws InterruptedException {
        Thread.sleep(ms);
        return name + " gotowe po " + ms + " ms";
    }
}
