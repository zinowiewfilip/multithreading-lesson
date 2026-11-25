package pl.lesson.multithreading.exercise;

/**
 * ĆWICZENIE (3 min, w parach)
 *
 * Policz sumę liczb od 1 do 1 000 000 w 4 wątkach – każdy wątek liczy ćwiartkę zakresu.
 * Wypisz wynik dopiero, gdy wszystkie wątki skończą. Oczekiwany wynik: 500000500000.
 *
 * Wersja 1: zwykłe Thread + join() + AtomicLong.
 * Wersja 2: ExecutorService + Future<Long>.
 *
 * Test: ParallelSumTest. Rozwiązanie: ParallelSumSolution (nie zaglądaj od razu).
 */
public class ParallelSum {

    public static long sum(long n, int threads) throws Exception {
        // TODO: podziel zakres 1..n na `threads` części i policz sumę równolegle
        throw new UnsupportedOperationException("Do zrobienia");
    }

    public static void main(String[] args) throws Exception {
        System.out.println(sum(1_000_000, 4));
    }
}
