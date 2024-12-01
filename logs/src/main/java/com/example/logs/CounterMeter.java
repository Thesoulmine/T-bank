package com.example.logs;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CounterMeter {

    private final Counter testCounter;

    public CounterMeter(MeterRegistry meterRegistry) {
        this.testCounter = meterRegistry.counter("test_counter");
    }
}
