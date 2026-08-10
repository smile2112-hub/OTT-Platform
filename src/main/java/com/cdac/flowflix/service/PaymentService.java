/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.PaymentDTO;
import com.cdac.flowflix.model.Payment;

public interface PaymentService {

    PaymentDTO createPayment(Long subscriptionId);

    String verifyPayment(Long paymentId);


// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

    // Verify payment by any id/ref â€” for dummy flows accept any input and mark
    // the current user's most recent PENDING payment as SUCCESS.
    String verifyAny(String any);

    List<PaymentDTO> getMyPayments();

    List<PaymentDTO> getAllPayments();

    Payment findById(Long id);

    Long getTotalRevenue();

    Long getMonthlyRevenue();

    Long getTodayRevenue();

}
