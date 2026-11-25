package pl.lesson.multithreading.part4_spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * 4.2 Serwis z metodami @Async.
 */
@Service
public class ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    @Async
    public CompletableFuture<String> generate(String name) throws InterruptedException {
        log.info("Generuję raport {} w wątku {}", name, Thread.currentThread().getName());
        Thread.sleep(2000); // symulacja wolnej operacji (baza, zewnętrzne API)
        return CompletableFuture.completedFuture("Raport " + name);
    }

    @Async
    public void sendEmail(String to) {
        log.info("Wysyłam maila do {} w wątku {}", to, Thread.currentThread().getName());
        if (to.isBlank()) {
            throw new IllegalArgumentException("Pusty adres"); // trafi do AsyncUncaughtExceptionHandler
        }
    }

    /**
     * PUŁAPKA: self-invocation. this.generate() omija proxy Springa,
     * więc wszystko wykona się synchronicznie w wątku żądania HTTP (~6 s).
     */
    public String generateAllWrong() throws Exception {
        String a = this.generate("A").get();
        String b = this.generate("B").get();
        String c = this.generate("C").get();
        return a + ", " + b + ", " + c;
    }
}
