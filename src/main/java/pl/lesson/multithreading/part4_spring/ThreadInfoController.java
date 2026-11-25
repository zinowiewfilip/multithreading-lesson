package pl.lesson.multithreading.part4_spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * GET /thread – pokazuje, w jakim wątku Tomcat obsłużył żądanie.
 * Domyślnie: http-nio-8080-exec-N (wątki platformowe).
 * Z spring.threads.virtual.enabled=true: wątek wirtualny.
 */
@RestController
public class ThreadInfoController {

    @GetMapping("/thread")
    public Map<String, Object> thread() {
        Thread t = Thread.currentThread();
        return Map.of(
                "name", t.getName(),
                "virtual", t.isVirtual(),
                "toString", t.toString());
    }
}
