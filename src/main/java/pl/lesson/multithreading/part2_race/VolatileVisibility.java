package pl.lesson.multithreading.part2_race;

/**
 * Do czego volatile JEST potrzebne: widoczność flagi między wątkami.
 * Usuń volatile – na wielu JVM wątek nigdy nie zobaczy zmiany i pętla nie skończy się.
 */
public class VolatileVisibility {

    static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            long loops = 0;
            while (running) {
                loops++;
            }
            System.out.println("Worker zatrzymany po " + loops + " obrotach");
        });
        worker.start();

        Thread.sleep(500);
        running = false;
        worker.join();
    }
}
