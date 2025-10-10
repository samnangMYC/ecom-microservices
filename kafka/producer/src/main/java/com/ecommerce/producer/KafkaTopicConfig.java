package com.ecommerce.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic newTopic() {
        return new NewTopic("my-topic", 3, (short) 1);
    }
}
