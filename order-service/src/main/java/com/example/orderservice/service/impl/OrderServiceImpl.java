package com.example.orderservice.service.impl;

import com.example.orderservice.dto.request.OrderRequestDTO;
import com.example.orderservice.dto.response.OrderResponseDTO;
import com.example.orderservice.entity.Order;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {
        Order order = orderMapper.toEntity(orderRequest);
        order = orderRepository.save(order);
        return orderMapper.toResponseDTO(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());    }

    @Override
    public OrderResponseDTO getOrderById(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toResponseDTO(order);
    }

    @Override
    public OrderResponseDTO updateOrder(Integer orderId, OrderRequestDTO orderRequest) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        order.setOrderDate(LocalDateTime.now());

        if (orderRequest.getOrderDesc() != null) {
            order.setOrderDesc(orderRequest.getOrderDesc());
        }
        if (orderRequest.getOrderFee() != null) {
            order.setOrderFee(orderRequest.getOrderFee());
        }
        if (orderRequest.getProductId() != null) {
            order.setProductId(orderRequest.getProductId());
        }


        return orderMapper.toResponseDTO(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }
    @Transactional
    public void payOrder(Integer orderId){
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.markOrderAsPaid(orderId);
    }
}
