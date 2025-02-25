package com.example.SpringSecurityDemo.Service.impl;

import com.example.SpringSecurityDemo.Service.PaymentService;
import com.example.SpringSecurityDemo.model.Dto.PaymentRequest;
import com.example.SpringSecurityDemo.model.Dto.PaymentResponse;
import com.example.SpringSecurityDemo.model.Payment;
import com.example.SpringSecurityDemo.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());

        payment = paymentRepository.save(payment);

        return new PaymentResponse(
                UUID.randomUUID().toString(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus()
        );
    }
}
