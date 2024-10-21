package ru.tbank.restful.limiter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

@Component
public class KudaGoRateLimiter {

    private final Semaphore semaphore;

    public KudaGoRateLimiter(@Value("${limiter.kuda-go.max-concurrent-requests}") int maxConcurrentRequests) {
        this.semaphore = new Semaphore(maxConcurrentRequests);
    }

    public void acquire() throws InterruptedException {
        semaphore.acquire();
    }

    public void release() {
        semaphore.release();
    }
}
