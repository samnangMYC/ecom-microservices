package com.ecommerce.notification;

import com.ecommerce.notification.payload.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class OrderEventConsumer {

    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return event -> {
            log.info("Received OrderCreatedEvent: {}", event.getOrderId());
            log.info("Received User OrderCreatedEvent: {}", event.getUserId());
        };
    }

}
