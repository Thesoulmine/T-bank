package ru.tbank.restful.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ExecutorsConfiguration {

    private final AtomicInteger threadPoolNumber = new AtomicInteger(1);

    private final String threadNamePrefix = "fixed-repository-initialization-thread-pool";

    @Qualifier("fixedRepositoryInitializationExecutorService")
    @Bean
    public ExecutorService fixedRepositoryInitializationExecutorService(
            @Value("${executor-service.fixed-repository-initialization.thread-number}") int threadNumber) {
        return Executors.newFixedThreadPool(
                threadNumber,
                r -> new Thread(r, threadNamePrefix + threadPoolNumber.getAndIncrement()));
    }

    @Qualifier("scheduledRepositoryInitializationExecutorService")
    @Bean
    public ScheduledExecutorService scheduledRepositoryInitializationExecutorService() {
        return Executors.newScheduledThreadPool(1);
    }
}
