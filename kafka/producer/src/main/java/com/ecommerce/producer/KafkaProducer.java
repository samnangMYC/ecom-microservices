//package com.ecommerce.producer;
//
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//public class KafkaProducer {
//
//    private final KafkaTemplate<String, RiderLocation> kafkaTemplate;
//
//    public KafkaProducer(KafkaTemplate<String, RiderLocation> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    @PostMapping("/send")
//    public String sendMessage(@RequestParam String message) {
//        RiderLocation location =
//                new RiderLocation("rider123",23.23,56.34);
//
//        kafkaTemplate.send("my-topic", location);
//        return "Message sending: " + location.getRiderId();
//    }
//}
