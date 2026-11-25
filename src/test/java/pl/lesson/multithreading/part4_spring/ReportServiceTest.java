package pl.lesson.multithreading.part4_spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReportServiceTest {

    @Autowired
    ReportService service;

    @Test
    void threeReportsRunInParallel() throws Exception {
        long start = System.currentTimeMillis();

        var a = service.generate("A");
        var b = service.generate("B");
        var c = service.generate("C");
        CompletableFuture.allOf(a, b, c).join();

        long elapsed = System.currentTimeMillis() - start;
        assertThat(a.get()).isEqualTo("Raport A");
        assertThat(elapsed).isLessThan(4000); // sekwencyjnie byłoby ~6000 ms
    }
}
