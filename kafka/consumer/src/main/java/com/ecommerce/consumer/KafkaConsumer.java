//package com.ecommerce.consumer;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KafkaConsumer {
//
//    @KafkaListener(topics = "my-topic",groupId = "my-new-group")
//    public void listen(String msg) {
//        System.out.println("Message received: " + msg);
//    }
//
//    @KafkaListener(topics = "my-topic",groupId = "my-new-group-1")
//    public void listen2(String msg) {
//        System.out.println("Received Message 2: " + msg);
//    }
//
//    @KafkaListener(topics = "my-topic",groupId = "my-new-group-2")
//    public void listenRiderLocation(RiderLocation riderLocation) {
//        System.out.println("Received Message 3: " + riderLocation);
//    }
//}
