package com.example.SpringSecurityDemo.Service;


import com.example.SpringSecurityDemo.model.Dto.PaymentRequest;
import com.example.SpringSecurityDemo.model.Dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest request);
}
