package pl.lesson.multithreading.exercise;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelSumTest {

    private static final long EXPECTED = 500_000_500_000L;

    @Test
    @Disabled("Usuń @Disabled, gdy zaimplementujesz ParallelSum.sum")
    void studentSolution() throws Exception {
        assertThat(ParallelSum.sum(1_000_000, 4)).isEqualTo(EXPECTED);
    }

    @Test
    void solutionWithThreads() throws Exception {
        assertThat(ParallelSumSolution.withThreads(1_000_000, 4)).isEqualTo(EXPECTED);
    }

    @Test
    void solutionWithExecutor() throws Exception {
        assertThat(ParallelSumSolution.withExecutor(1_000_000, 4)).isEqualTo(EXPECTED);
    }

    @Test
    void unevenSplit() throws Exception {
        assertThat(ParallelSumSolution.withExecutor(10, 3)).isEqualTo(55);
    }
}
