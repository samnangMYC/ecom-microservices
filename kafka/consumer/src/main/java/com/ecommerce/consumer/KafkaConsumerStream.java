package com.ecommerce.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class KafkaConsumerStream {
    @Bean
    public Consumer<RiderLocation> processRiderLocation() {
        return riderLocation -> {
            System.out.println("Rider location: " + riderLocation);
        };
    }
    @Bean
    public Consumer<RiderLocation> processRiderStatus() {
        return riderLocation -> {
            System.out.println("Rider Status: " + riderLocation.getRiderId());
        };
    }
}
