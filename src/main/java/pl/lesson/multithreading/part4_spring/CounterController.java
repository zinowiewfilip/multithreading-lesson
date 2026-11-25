package pl.lesson.multithreading.part4_spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Race condition w Springu: beany są domyślnie singletonami, więc pole jest współdzielone
 * przez wszystkie wątki Tomcata obsługujące żądania.
 *
 * Demo (zob. README): zresetuj liczniki, wyślij 1000 równoległych POST /counter, potem GET /counter.
 */
@RestController
public class CounterController {

    private int unsafeCounter = 0;                        // ŹLE
    private final AtomicInteger safeCounter = new AtomicInteger(); // DOBRZE

    @PostMapping("/counter")
    public void increment() {
        for (int i = 0; i < 1000; i++) { // pętla zwiększa szansę na kolizję
            unsafeCounter++;
            safeCounter.incrementAndGet();
        }
    }

    @GetMapping("/counter")
    public Map<String, Integer> get() {
        return Map.of("unsafe", unsafeCounter, "safe", safeCounter.get());
    }

    @PostMapping("/counter/reset")
    public void reset() {
        unsafeCounter = 0;
        safeCounter.set(0);
    }
}
