package pl.lesson.multithreading.part1_threads;

/**
 * 1.4 Cykl życia wątku: NEW -> RUNNABLE -> (BLOCKED / WAITING / TIMED_WAITING) -> TERMINATED.
 */
public class ThreadStates {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        Thread sleeper = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        });
        System.out.println("sleeper: " + sleeper.getState()); // NEW
        sleeper.start();
        Thread.sleep(100);
        System.out.println("sleeper: " + sleeper.getState()); // TIMED_WAITING

        // BLOCKED: wątek czeka na monitor zajęty przez main
        Thread blocked = new Thread(() -> {
            synchronized (lock) {
                System.out.println("blocked: wszedłem do sekcji");
            }
        });
        synchronized (lock) {
            blocked.start();
            Thread.sleep(100);
            System.out.println("blocked: " + blocked.getState()); // BLOCKED
        }

        sleeper.join();
        blocked.join();
        System.out.println("sleeper: " + sleeper.getState()); // TERMINATED
    }
}
