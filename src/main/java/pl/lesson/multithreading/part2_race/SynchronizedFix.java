package pl.lesson.multithreading.part2_race;

/**
 * Rozwiązanie A: synchronized – tylko jeden wątek naraz w sekcji krytycznej.
 */
public class SynchronizedFix {

    static int counter = 0;
    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                synchronized (lock) {
                    counter++;
                }
            }
        };
        Thread a = new Thread(task);
        Thread b = new Thread(task);
        a.start();
        b.start();
        a.join();
        b.join();
        System.out.println("synchronized: " + counter);
    }
}
