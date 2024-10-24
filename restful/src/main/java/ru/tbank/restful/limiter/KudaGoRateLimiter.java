package ru.tbank.restful.limiter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

@Qualifier("KudaGoRateLimiter")
@Component
public class KudaGoRateLimiter implements RateLimiter {

    private final Semaphore semaphore;

    public KudaGoRateLimiter(@Value("${limiter.kuda-go.max-concurrent-requests}") int maxConcurrentRequests) {
        this.semaphore = new Semaphore(maxConcurrentRequests);
    }

    @Override
    public void acquire() throws InterruptedException {
        semaphore.acquire();
    }

    @Override
    public void release() {
        semaphore.release();
    }
}
