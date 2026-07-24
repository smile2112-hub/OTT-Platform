package com.cdac.flowflix.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.PaymentDTO;
import com.cdac.flowflix.model.Payment;
import com.cdac.flowflix.repository.PaymentRepository;
import com.cdac.flowflix.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment savePayment(PaymentDTO dto) {

        Payment payment = new Payment();

        payment.setUsername(dto.getUsername());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setTransactionId(dto.getTransactionId());
        payment.setPlan(dto.getPlan());

        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPayments(String username) {

        return paymentRepository.findByUsername(username);

    }
}