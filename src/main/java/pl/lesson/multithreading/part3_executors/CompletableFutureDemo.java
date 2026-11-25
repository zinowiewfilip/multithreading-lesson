package pl.lesson.multithreading.part3_executors;

import java.util.concurrent.CompletableFuture;

/**
 * 3.2 CompletableFuture – łączenie zadań bez ręcznego blokowania.
 * Dwa "zapytania" lecą równolegle, czas = max z obu.
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        CompletableFuture<String> user = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "Anna";
        });
        CompletableFuture<Integer> orders = CompletableFuture.supplyAsync(() -> {
            sleep(700);
            return 7;
        });

        String result = user
                .thenCombine(orders, (u, o) -> u + " ma " + o + " zamówień")
                .thenApply(String::toUpperCase)
                .exceptionally(ex -> "Błąd: " + ex.getMessage())
                .join();

        System.out.println(result + " (" + (System.currentTimeMillis() - start) + " ms)");

        // Obsługa błędu
        String fallback = CompletableFuture
                .supplyAsync(() -> {
                    if (true) throw new IllegalStateException("serwis nie odpowiada");
                    return "ok";
                })
                .exceptionally(ex -> "fallback, bo: " + ex.getCause().getMessage())
                .join();
        System.out.println(fallback);
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
