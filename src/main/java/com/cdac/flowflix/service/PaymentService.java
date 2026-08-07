package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.PaymentDTO;
import com.cdac.flowflix.model.Payment;

public interface PaymentService {

    PaymentDTO createPayment(Long subscriptionId);

    String verifyPayment(Long paymentId);

    // Verify payment by any id/ref — for dummy flows accept any input and mark
    // the current user's most recent PENDING payment as SUCCESS.
    String verifyAny(String any);

    List<PaymentDTO> getMyPayments();

    List<PaymentDTO> getAllPayments();

    Payment findById(Long id);

    Long getTotalRevenue();

    Long getMonthlyRevenue();

    Long getTodayRevenue();

}