package pl.lesson.multithreading.part2_race;

/**
 * volatile daje widoczność, ale nie atomowość – wynik nadal jest zły.
 */
public class VolatileNotEnough {

    static volatile int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                counter++;
            }
        };
        Thread a = new Thread(task);
        Thread b = new Thread(task);
        a.start();
        b.start();
        a.join();
        b.join();
        System.out.println("volatile – oczekiwane 200000, jest: " + counter);
    }
}
