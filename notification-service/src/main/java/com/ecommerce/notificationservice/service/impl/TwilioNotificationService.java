package com.ecommerce.notificationservice.service.impl;

import com.ecommerce.notificationservice.config.TwilioConfig;
import com.ecommerce.notificationservice.dto.request.OrderEvent;
import com.ecommerce.notificationservice.dto.request.PaymentEvent;
import com.ecommerce.notificationservice.exception.customExceptions.SmsDeliveryException;
import com.ecommerce.notificationservice.service.NotificationService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwilioNotificationService implements NotificationService {

    private final TwilioConfig twilioConfig;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void sendPaymentConfirmation(PaymentEvent paymentEvent) {
        try {
            String message = String.format(
                    "Payment Confirmation: Your payment of $%s for order #%d has been successfully processed on %s. Thank you for your purchase!",
                    DECIMAL_FORMAT.format(paymentEvent.amount()),
                    paymentEvent.orderId(),
                    paymentEvent.paymentDate().format(DATE_FORMATTER)
            );

            sendSms(paymentEvent.userPhoneNumber(), message);
//            log.info("Payment confirmation sent to user ID: {}", paymentEvent.userId());
        } catch (Exception e) {
//            log.error("Failed to send payment confirmation: {}", e.getMessage(), e);
        }
    }

    @Override
    public void sendOrderShippedNotification(OrderEvent orderEvent) {
        try {
            String message = String.format(
                    "Your order #%d has been shipped on %s. Track your package with tracking number: %s. Thank you for shopping with us!",
                    orderEvent.orderId(),
                    orderEvent.shippingDate().format(DATE_FORMATTER),
                    orderEvent.trackingNumber()
            );

            sendSms(orderEvent.userPhoneNumber(), message);
//            log.info("Order shipped notification sent to user ID: {}", orderEvent.userId());
        } catch (Exception e) {
//
        }
    }

    @Override
    public void sendTestNotification(String phoneNumber, String message) {
        try {
            log.info("Attempting to send SMS to: {}", phoneNumber);
            sendSms(phoneNumber, message);
            log.info("SMS sent successfully to: {}", phoneNumber);
        } catch (Exception e) {
            log.error("SMS delivery failed to: {}", phoneNumber, e);
            throw new SmsDeliveryException(phoneNumber,
                    "Failed to deliver SMS notification: " + e.getMessage(), e);
        }
    }

    private void sendSms(String to, String messageBody) {
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioConfig.getPhoneNumber()),
                messageBody
        ).create();

    }
}