package com.example.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Integer orderId;
    private LocalDateTime orderDate;
    private String orderDesc;
    private Double orderFee;
    private boolean isPayed;
    private Integer productId;
    private Integer userId;
}