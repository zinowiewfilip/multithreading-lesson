package pl.lesson.multithreading.part3_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 3.3 Wątki wirtualne (Java 21). 10 000 zadań po 1 s:
 * - wirtualne: ~1 s
 * - pula 100 wątków platformowych: ~100 s (dlatego domyślnie liczymy tylko 1 000 zadań)
 * Wątki wirtualne pomagają przy czekaniu (I/O), nie przy obliczeniach CPU.
 */
public class VirtualThreadsDemo {

    public static void main(String[] args) {
        run("wirtualne, 10 000 zadań", Executors.newVirtualThreadPerTaskExecutor(), 10_000);
        run("pula 100 platformowych, 1 000 zadań", Executors.newFixedThreadPool(100), 1_000);
    }

    static void run(String label, ExecutorService executor, int tasks) {
        long start = System.currentTimeMillis();
        try (executor) {
            for (int i = 0; i < tasks; i++) {
                executor.submit(() -> {
                    Thread.sleep(1000); // blokujące I/O
                    return null;
                });
            }
        }
        System.out.printf("%s: %d ms%n", label, System.currentTimeMillis() - start);
    }
}
