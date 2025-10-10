package com.ecommerce.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class KafkaProducerStreams {

    @Bean
    public Supplier<RiderLocation> sendRiderLocation() {
        return () -> {
            String riderId = UUID.randomUUID().toString();
            RiderLocation riderLocation = new RiderLocation("rider" + riderId,34,34.22);
            System.out.println("Sending Rider location: " + riderLocation.getRiderId());
            return riderLocation;
        };
    }


}
