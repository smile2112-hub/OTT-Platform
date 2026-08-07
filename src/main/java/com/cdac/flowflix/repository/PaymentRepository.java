package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.flowflix.model.Payment;
import com.cdac.flowflix.model.PaymentStatus;
import com.cdac.flowflix.model.Subscription;
import com.cdac.flowflix.model.User;

@Repository
public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    // ==========================================
    // USER PAYMENTS
    // ==========================================

    List<Payment> findByUserOrderByPaymentDateDesc(
            User user);

    // ==========================================
    // SUBSCRIPTION PAYMENTS
    // ==========================================

    List<Payment> findBySubscriptionOrderByPaymentDateDesc(
            Subscription subscription);

    // ==========================================
    // USER'S SUBSCRIPTION PAYMENTS
    // ==========================================

    List<Payment> findBySubscriptionUser(
            User user);

    // ==========================================
    // PAYMENT STATUS
    // ==========================================

    List<Payment> findByPaymentStatus(
            PaymentStatus paymentStatus);

    // ==========================================
    // TRANSACTION
    // ==========================================

    Payment findByTransactionId(
            String transactionId);

    Payment findByRazorpayPaymentId(
            String razorpayPaymentId);

    Payment findByRazorpayOrderId(
            String razorpayOrderId);

    Payment findByStripePaymentIntentId(
            String stripePaymentIntentId);

    // ==========================================
    // ADMIN DASHBOARD
    // ==========================================

    Long countByPaymentStatus(
            PaymentStatus paymentStatus);

    List<Payment> findTop10ByOrderByPaymentDateDesc();

    List<Payment> findTop10ByOrderByAmountDesc();

}