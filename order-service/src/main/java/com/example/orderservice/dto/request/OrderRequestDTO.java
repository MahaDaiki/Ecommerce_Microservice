package com.example.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    //private LocalDateTime orderDate;
    private String orderDesc;
    private Double orderFee;
    private Integer productId;
    private Integer userId;
}
