package pl.lesson.multithreading.part1_threads;

import java.util.ArrayList;
import java.util.List;

/**
 * Dlaczego nie tworzymy wątku na każde zadanie: każdy wątek platformowy to wątek systemu
 * operacyjnego z własnym stosem (~1 MB). Most do części 3 (pule i wątki wirtualne).
 */
public class TooManyThreads {

    public static void main(String[] args) throws InterruptedException {
        int count = 2_000;
        long start = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            });
            t.start();
            threads.add(t);
        }
        for (Thread t : threads) {
            t.join();
        }

        System.out.printf("%d wątków platformowych: %d ms%n", count, System.currentTimeMillis() - start);
    }
}
