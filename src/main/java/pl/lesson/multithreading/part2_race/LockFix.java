package pl.lesson.multithreading.part2_race;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Rozwiązanie C: ReentrantLock – jawna blokada. Daje tryLock, timeout, sprawiedliwą kolejkę.
 * unlock() zawsze w finally.
 */
public class LockFix {

    static int counter = 0;
    static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                lock.lock();
                try {
                    counter++;
                } finally {
                    lock.unlock();
                }
            }
        };
        Thread a = new Thread(task);
        Thread b = new Thread(task);
        a.start();
        b.start();
        a.join();
        b.join();
        System.out.println("ReentrantLock: " + counter);
    }
}
