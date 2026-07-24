package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.flowflix.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

    List<Payment> findByUsername(String username);

}