package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.PaymentDTO;
import com.cdac.flowflix.model.Payment;
import com.cdac.flowflix.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/save")
    public ResponseEntity<Payment> savePayment(
            @RequestBody PaymentDTO dto) {

        return ResponseEntity.ok(
                paymentService.savePayment(dto));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Payment>> getPayments(
            @PathVariable String username) {

        return ResponseEntity.ok(
                paymentService.getPayments(username));
    }
}