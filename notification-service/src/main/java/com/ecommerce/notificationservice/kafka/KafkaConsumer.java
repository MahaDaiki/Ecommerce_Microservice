package com.ecommerce.notificationservice.kafka;

import com.ecommerce.notificationservice.dto.request.OrderEvent;
import com.ecommerce.notificationservice.dto.request.PaymentEvent;
import com.ecommerce.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${app.kafka.topics.payment-completed}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePaymentCompletedEvent(PaymentEvent paymentEvent) {
        log.info("Received payment completed event: {}", paymentEvent);
        notificationService.sendPaymentConfirmation(paymentEvent);
    }

    @KafkaListener(topics = "${app.kafka.topics.order-shipped}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderShippedEvent(OrderEvent orderEvent) {
        log.info("Received order shipped event: {}", orderEvent);
        notificationService.sendOrderShippedNotification(orderEvent);
    }
}