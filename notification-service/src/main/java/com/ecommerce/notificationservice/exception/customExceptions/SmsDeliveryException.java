package com.ecommerce.notificationservice.exception.customExceptions;

public class SmsDeliveryException extends NotificationException {
    private final String phoneNumber;

    public SmsDeliveryException(String phoneNumber, String message, Throwable cause) {
        super(message, cause);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}