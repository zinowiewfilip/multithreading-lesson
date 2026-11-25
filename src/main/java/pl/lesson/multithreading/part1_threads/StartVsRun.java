package pl.lesson.multithreading.part1_threads;

/**
 * 1.2 start() vs run().
 * run() to zwykłe wywołanie metody w bieżącym wątku, start() tworzy nowy wątek.
 */
public class StartVsRun {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(
                () -> System.out.println("Działam w: " + Thread.currentThread().getName()));

        t.run();   // Działam w: main
        t.start(); // Działam w: Thread-0
        t.join();

        try {
            t.start(); // wątku nie da się uruchomić drugi raz
        } catch (IllegalThreadStateException e) {
            System.out.println("Drugi start() -> " + e.getClass().getSimpleName());
        }
    }
}
