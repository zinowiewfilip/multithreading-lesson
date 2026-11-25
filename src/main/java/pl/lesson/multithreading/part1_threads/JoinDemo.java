package pl.lesson.multithreading.part1_threads;

/**
 * 1.3 sleep i join.
 * Zakomentuj worker.join() i zobacz, że "main kończy" pojawia się przed krokami.
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Krok " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // przywracamy flagę przerwania
                    return;
                }
            }
        }, "worker");

        worker.start();
        System.out.println("main czeka...");
        worker.join(); // main blokuje się do końca workera
        System.out.println("worker skończył, main kończy");
    }
}
