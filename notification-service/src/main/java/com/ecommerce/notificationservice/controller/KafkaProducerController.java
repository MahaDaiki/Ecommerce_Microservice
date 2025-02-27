package com.ecommerce.notificationservice.controller;

import com.ecommerce.notificationservice.dto.request.OrderEvent;
import com.ecommerce.notificationservice.dto.request.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/test-producer")
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topics.payment-completed}")
    private String paymentTopic;

    @Value("${app.kafka.topics.order-shipped}")
    private String orderTopic;

    @PostMapping("/payment")
    public ResponseEntity<Map<String, String>> publishPaymentEvent(@RequestBody PaymentEvent event) {
        log.info("Publishing payment event to Kafka: {}", event);
        kafkaTemplate.send(paymentTopic, event);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Payment event published to Kafka");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/order")
    public ResponseEntity<Map<String, String>> publishOrderEvent(@RequestBody OrderEvent event) {
        log.info("Publishing order event to Kafka: {}", event);
        kafkaTemplate.send(orderTopic, event);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Order event published to Kafka");

        return ResponseEntity.ok(response);
    }
}