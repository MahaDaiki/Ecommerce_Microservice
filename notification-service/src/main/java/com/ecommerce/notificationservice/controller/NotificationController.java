package com.ecommerce.notificationservice.controller;

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
}