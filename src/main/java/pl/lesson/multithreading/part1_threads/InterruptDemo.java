package pl.lesson.multithreading.part1_threads;

/**
 * Grzeczne zatrzymywanie wątku przez interrupt().
 * Nie ma bezpiecznego Thread.stop() – wątek sam sprawdza flagę przerwania.
 */
public class InterruptDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Pracuję... " + i++);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    System.out.println("Dostałem interrupt podczas sleep");
                    Thread.currentThread().interrupt(); // sleep czyści flagę – ustawiamy ją z powrotem
                }
            }
            System.out.println("Sprzątam i kończę");
        });

        worker.start();
        Thread.sleep(1000);
        worker.interrupt();
        worker.join();
    }
}
