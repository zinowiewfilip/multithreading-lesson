package pl.lesson.multithreading.part1_threads;

/**
 * 1.1 Pierwszy wątek – dwa sposoby tworzenia wątku.
 * Uruchom kilka razy: kolejność wypisów się zmienia, decyduje o niej planista systemu.
 */
public class FirstThread {

    public static void main(String[] args) {
        // Sposób 1: dziedziczenie po Thread
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("Hello z " + Thread.currentThread().getName());
            }
        };

        // Sposób 2: Runnable jako lambda (preferowany – oddziela zadanie od wątku)
        Thread t2 = new Thread(
                () -> System.out.println("Hello z " + Thread.currentThread().getName()),
                "worker-2");

        t1.start();
        t2.start();
        System.out.println("Hello z " + Thread.currentThread().getName());
    }
}
