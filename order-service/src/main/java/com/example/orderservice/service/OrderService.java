package com.example.orderservice.service;

import com.example.orderservice.dto.request.OrderRequestDTO;
import com.example.orderservice.dto.response.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO productRequest);
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderById(Integer productId);
    OrderResponseDTO updateOrder(Integer productId, OrderRequestDTO productRequest);
    void deleteOrder(Integer productId);
}
