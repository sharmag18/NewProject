package com.ecommerce.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Value("${app.rate-limit.requests-per-minute:100}")
    private int requestsPerMinute;

    @Value("${app.rate-limit.burst-capacity:20}")
    private int burstCapacity;

    @Bean
    public Bucket rateLimitBucket() {
        Bandwidth limit = Bandwidth.classic(burstCapacity, Refill.greedy(requestsPerMinute, Duration.ofMinutes(1)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
} 