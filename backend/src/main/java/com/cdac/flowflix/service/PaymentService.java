package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.PaymentDTO;
import com.cdac.flowflix.model.Payment;

public interface PaymentService {

    Payment savePayment(PaymentDTO dto);

    List<Payment> getPayments(String username);

}