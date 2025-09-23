package com.samnang.order.controller;


import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MessageController {

    @Value("${app.message}")
    private String message;

    @GetMapping("/message")
    @RateLimiter(name = "retryBreaker",fallbackMethod = "fallbackRetry" )
    public String getMessage() {
        return message;
    }

    public String fallbackRetry(Exception e) {
        return "fallback init";
    }
}
