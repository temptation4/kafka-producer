package com.example.order_producer.service;

import com.example.order_producer.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "orders-topic";

    public OrderProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(String message) {
        kafkaTemplate.send(TOPIC, message);
        System.out.println("Sent message: " + message);
    }

    public void sendEventsToTopic(Order order) {
        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC,0,"101", order);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + order.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            order.toString() + "] due to : " + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Scheduled(initialDelay = 3000,fixedDelay = 1000) // 10 seconds
    public void sendScheduledOrders() {
        Random r= new Random();
        int id = r.nextInt(1000);
        double price = r.nextDouble(100000);
        String name ="";
        if(id%2 == 0) {
            name = "Phone";
        } else {
            name = "Laptop";
        }

        Order order = new Order( id, name, price, "Electronics");
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC,2,"DLT-testing", order);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Scheduled Order sent: " + order);
            } else {
                System.err.println("Error sending order: " + ex.getMessage());
            }
        });
    }


}

