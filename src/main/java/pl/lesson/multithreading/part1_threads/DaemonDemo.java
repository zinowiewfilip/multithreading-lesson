package pl.lesson.multithreading.part1_threads;

/**
 * Wątek demon nie trzyma JVM przy życiu.
 * Zmień setDaemon(true) na false – program nigdy się nie skończy.
 */
public class DaemonDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread heartbeat = new Thread(() -> {
            while (true) {
                System.out.println("tik");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        heartbeat.setDaemon(true); // musi być przed start()
        heartbeat.start();

        Thread.sleep(700);
        System.out.println("main kończy – JVM zamyka się mimo działającego demona");
    }
}
