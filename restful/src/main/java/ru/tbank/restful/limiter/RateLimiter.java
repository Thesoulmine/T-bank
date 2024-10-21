package ru.tbank.restful.limiter;

public interface RateLimiter {

    void acquire() throws InterruptedException;

    void release();
}
