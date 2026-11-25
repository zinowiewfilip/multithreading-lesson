package pl.lesson.multithreading.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

/**
 * Rozwiązanie ćwiczenia – dwie wersje.
 */
public class ParallelSumSolution {

    /** Wersja 1: zwykłe wątki + join + AtomicLong. */
    public static long withThreads(long n, int threads) throws InterruptedException {
        AtomicLong total = new AtomicLong();
        List<Thread> workers = new ArrayList<>();
        long chunk = n / threads;

        for (int i = 0; i < threads; i++) {
            long from = i * chunk + 1;
            long to = (i == threads - 1) ? n : (i + 1) * chunk; // ostatni bierze resztę
            Thread t = new Thread(() -> total.addAndGet(LongStream.rangeClosed(from, to).sum()));
            t.start();
            workers.add(t);
        }
        for (Thread t : workers) {
            t.join();
        }
        return total.get();
    }

    /** Wersja 2: pula wątków + Future. */
    public static long withExecutor(long n, int threads) throws Exception {
        try (ExecutorService pool = Executors.newFixedThreadPool(threads)) {
            List<Future<Long>> parts = new ArrayList<>();
            long chunk = n / threads;

            for (int i = 0; i < threads; i++) {
                long from = i * chunk + 1;
                long to = (i == threads - 1) ? n : (i + 1) * chunk;
                parts.add(pool.submit(() -> LongStream.rangeClosed(from, to).sum()));
            }

            long total = 0;
            for (Future<Long> part : parts) {
                total += part.get();
            }
            return total;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Thread + join:    " + withThreads(1_000_000, 4));
        System.out.println("ExecutorService:  " + withExecutor(1_000_000, 4));
    }
}
