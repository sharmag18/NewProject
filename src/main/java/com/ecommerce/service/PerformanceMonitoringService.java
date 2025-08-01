package com.ecommerce.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PerformanceMonitoringService {

    private final MeterRegistry meterRegistry;
    private final Counter totalRequestsCounter;
    private final Counter errorRequestsCounter;
    private final Timer requestTimer;

    public PerformanceMonitoringService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        
        // Initialize counters and timers
        this.totalRequestsCounter = Counter.builder("api.requests.total")
                .description("Total number of API requests")
                .register(meterRegistry);
        
        this.errorRequestsCounter = Counter.builder("api.requests.errors")
                .description("Total number of API errors")
                .register(meterRegistry);
        
        this.requestTimer = Timer.builder("api.requests.duration")
                .description("API request duration")
                .register(meterRegistry);
    }

    public void incrementTotalRequests() {
        totalRequestsCounter.increment();
    }

    public void incrementErrorRequests() {
        errorRequestsCounter.increment();
    }

    public Timer.Sample startTimer() {
        return Timer.start(meterRegistry);
    }

    public void stopTimer(Timer.Sample sample) {
        sample.stop(requestTimer);
    }

    public void recordRequestDuration(long duration, TimeUnit timeUnit) {
        requestTimer.record(duration, timeUnit);
    }

    public double getTotalRequests() {
        return totalRequestsCounter.count();
    }

    public double getErrorRequests() {
        return errorRequestsCounter.count();
    }

    public double getSuccessRate() {
        double total = getTotalRequests();
        if (total == 0) return 100.0;
        return ((total - getErrorRequests()) / total) * 100;
    }
} 