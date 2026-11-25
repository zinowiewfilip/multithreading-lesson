package pl.lesson.multithreading.part2_race;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rozwiązanie B: AtomicInteger – operacja atomowa bez blokady (compare-and-swap).
 */
public class AtomicFix {

    static final AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                counter.incrementAndGet();
            }
        };
        Thread a = new Thread(task);
        Thread b = new Thread(task);
        a.start();
        b.start();
        a.join();
        b.join();
        System.out.println("AtomicInteger: " + counter.get());
    }
}
