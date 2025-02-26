package com.ecommerce.notificationservice.controller;

import com.ecommerce.notificationservice.dto.request.OrderEvent;
import com.ecommerce.notificationservice.dto.request.PaymentEvent;
import com.ecommerce.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendTestNotification(
            @RequestParam String phoneNumber,
            @RequestParam String message) {

        notificationService.sendTestNotification(phoneNumber, message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Test notification sent successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "up");
        response.put("service", "notification-service");

        return ResponseEntity.ok(response);
    }


    @PostMapping("/test/payment-completed")
    public ResponseEntity<Map<String, String>> testPaymentCompletedEvent(
            @RequestBody PaymentEvent paymentEvent) {

        log.info("Received test payment completed event: {}", paymentEvent);
        notificationService.sendPaymentConfirmation(paymentEvent);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Payment confirmation notification sent");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/test/order-shipped")
    public ResponseEntity<Map<String, String>> testOrderShippedEvent(
            @RequestBody OrderEvent orderEvent) {

        log.info("Received test order shipped event: {}", orderEvent);
        notificationService.sendOrderShippedNotification(orderEvent);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Order shipped notification sent");

        return ResponseEntity.ok(response);
    }
}