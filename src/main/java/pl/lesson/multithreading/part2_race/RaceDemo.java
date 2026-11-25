package pl.lesson.multithreading.part2_race;

/**
 * 2.1 Race condition. counter++ to trzy operacje (odczyt, dodanie, zapis), nie jedna.
 * Zapytaj salę przed uruchomieniem: ile wypisze?
 */
public class RaceDemo {

    static int counter = 0;

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
        System.out.println("Oczekiwane 200000, jest: " + counter);
    }
}
