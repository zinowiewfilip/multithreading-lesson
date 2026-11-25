package pl.lesson.multithreading.part2_race;

/**
 * Bonus: deadlock. Dwa wątki biorą dwie blokady w odwrotnej kolejności i czekają na siebie w nieskończoność.
 * Podczas działania uruchom: jstack <pid> – JVM sam wypisze "Found one Java-level deadlock".
 * Naprawa: zawsze brać blokady w tej samej kolejności.
 */
public class DeadlockDemo {

    static final Object lockA = new Object();
    static final Object lockB = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lockA) {
                sleep();
                synchronized (lockB) {
                    System.out.println("T1 ma obie blokady");
                }
            }
        }, "T1").start();

        new Thread(() -> {
            synchronized (lockB) {
                sleep();
                synchronized (lockA) {
                    System.out.println("T2 ma obie blokady");
                }
            }
        }, "T2").start();

        System.out.println("Program wisi – Ctrl+C, żeby zakończyć");
    }

    static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
