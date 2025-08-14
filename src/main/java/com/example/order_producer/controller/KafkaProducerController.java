package com.example.order_producer.controller;

import com.example.order_producer.model.Order;
import com.example.order_producer.service.OrderProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class KafkaProducerController {

    private final OrderProducerService producerService;

    public KafkaProducerController(OrderProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/send")
    public String sendOrderMessage(@RequestParam("message") String message) {
        producerService.sendOrder(message);
        return "Message sent: " + message;
    }

    @PostMapping("/publish")
    public void sendEvents(@RequestBody Order order) {
        producerService.sendEventsToTopic(order);
    }
}

