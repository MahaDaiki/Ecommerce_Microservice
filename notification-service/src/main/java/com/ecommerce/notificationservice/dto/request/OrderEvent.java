package com.ecommerce.notificationservice.dto.request;

import java.time.LocalDateTime;

public record OrderEvent(Integer orderId,
                         Integer userId,
                         String userPhoneNumber,
                         String orderStatus,
                         LocalDateTime shippingDate,
                         String trackingNumber) {
}
