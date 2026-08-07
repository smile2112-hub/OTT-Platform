package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.PaymentDTO;
import com.cdac.flowflix.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // ==========================================
    // CREATE PAYMENT
    // ==========================================

    @PostMapping("/create/{subscriptionId}")
    public ResponseEntity<PaymentDTO> createPayment(
            @PathVariable Long subscriptionId) {

        PaymentDTO payment =
                paymentService.createPayment(subscriptionId);

        if (payment == null) {

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(payment);

    }

    // ==========================================
    // VERIFY PAYMENT
    // ==========================================

    @PutMapping("/verify/{paymentId}")
    public ResponseEntity<String> verifyPayment(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(
                paymentService.verifyPayment(paymentId));

    }

        // Dummy verification: accepts any id/ref and verifies the current user's
        // most recent pending payment.
        @PutMapping("/verify-any/{any}")
        public ResponseEntity<String> verifyAny(
            @PathVariable String any) {

        return ResponseEntity.ok(
            paymentService.verifyAny(any));

        }

    // ==========================================
    // MY PAYMENTS
    // ==========================================

    @GetMapping("/my")
    public ResponseEntity<List<PaymentDTO>> getMyPayments() {

        return ResponseEntity.ok(
                paymentService.getMyPayments());

    }

    // ==========================================
    // ADMIN - ALL PAYMENTS
    // ==========================================

    @GetMapping("/admin/all")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {

        return ResponseEntity.ok(
                paymentService.getAllPayments());

    }

    // ==========================================
    // ADMIN - TOTAL REVENUE
    // ==========================================

    @GetMapping("/admin/revenue/total")
    public ResponseEntity<Long> getTotalRevenue() {

        return ResponseEntity.ok(
                paymentService.getTotalRevenue());

    }

    // ==========================================
    // ADMIN - MONTHLY REVENUE
    // ==========================================

    @GetMapping("/admin/revenue/month")
    public ResponseEntity<Long> getMonthlyRevenue() {

        return ResponseEntity.ok(
                paymentService.getMonthlyRevenue());

    }

    // ==========================================
    // ADMIN - TODAY REVENUE
    // ==========================================

    @GetMapping("/admin/revenue/today")
    public ResponseEntity<Long> getTodayRevenue() {

        return ResponseEntity.ok(
                paymentService.getTodayRevenue());

    }

}