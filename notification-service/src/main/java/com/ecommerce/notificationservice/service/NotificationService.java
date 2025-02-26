package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.request.OrderEvent;
import com.ecommerce.notificationservice.dto.request.PaymentEvent;

public interface NotificationService {
    void sendPaymentConfirmation(PaymentEvent paymentEvent);
    void sendOrderShippedNotification(OrderEvent orderEvent);
    void sendTestNotification(String phoneNumber, String message);
}
