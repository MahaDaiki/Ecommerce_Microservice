package com.ecommerce.notificationservice.dto.request;

import java.time.LocalDateTime;

public record PaymentEvent(Integer orderId,
                           Integer userId,
                           String userPhoneNumber,
                           Double amount,
                           LocalDateTime paymentDate,
                           String status) {
}
