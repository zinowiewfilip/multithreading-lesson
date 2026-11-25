package pl.lesson.multithreading.part4_spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 4.1 Włączenie @Async i własna pula wątków.
 * Bez własnej puli Spring Boot konfiguruje domyślny executor (wątki "task-N").
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);      // tyle wątków trzymamy stale
        executor.setMaxPoolSize(8);       // tyle maksymalnie, gdy kolejka jest pełna
        executor.setQueueCapacity(100);   // zadania czekające na wolny wątek
        executor.setThreadNamePrefix("async-");
        executor.initialize();
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor();
    }

    /** Wyjątki z metod @Async zwracających void inaczej znikają po cichu. */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) ->
                log.error("Błąd w metodzie @Async {}: {}", method.getName(), ex.getMessage());
    }
}
