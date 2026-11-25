package pl.lesson.multithreading.part4_spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    /** GET /reports – trzy raporty równolegle: ~2 s zamiast ~6 s. */
    @GetMapping("/reports")
    public Map<String, Object> reports() throws Exception {
        long start = System.currentTimeMillis();

        var a = service.generate("A");
        var b = service.generate("B");
        var c = service.generate("C");
        CompletableFuture.allOf(a, b, c).join();

        return Map.of(
                "reports", List.of(a.get(), b.get(), c.get()),
                "timeMs", System.currentTimeMillis() - start,
                "requestThread", Thread.currentThread().getName());
    }

    /** GET /reports/wrong – self-invocation, @Async nie działa: ~6 s. */
    @GetMapping("/reports/wrong")
    public Map<String, Object> reportsWrong() throws Exception {
        long start = System.currentTimeMillis();
        String result = service.generateAllWrong();
        return Map.of("reports", result, "timeMs", System.currentTimeMillis() - start);
    }

    /** POST /email?to= – fire-and-forget. Pusty "to" pokaże obsługę wyjątku w logach. */
    @PostMapping("/email")
    public String email(@RequestParam(defaultValue = "") String to) {
        service.sendEmail(to);
        return "Zlecono wysyłkę (odpowiedź wraca od razu)";
    }
}
