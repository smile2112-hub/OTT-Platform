package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.SubscriptionDTO;
import com.cdac.flowflix.model.SubscriptionPlan;
import com.cdac.flowflix.service.SubscriptionService;

@RestController
@RequestMapping("/api/subscription")
@CrossOrigin("*")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // ==========================================
    // PURCHASE SUBSCRIPTION
    // ==========================================

    @PostMapping("/purchase/{plan}")
    public ResponseEntity<String> purchaseSubscription(
            @PathVariable SubscriptionPlan plan) {

        return ResponseEntity.ok(

                subscriptionService.purchaseSubscription(plan));

    }

    // ==========================================
    // UPGRADE SUBSCRIPTION
    // ==========================================

    @PutMapping("/upgrade/{plan}")
    public ResponseEntity<String> upgradeSubscription(
            @PathVariable SubscriptionPlan plan) {

        return ResponseEntity.ok(

                subscriptionService.upgradeSubscription(plan));

    }

    // ==========================================
    // CANCEL SUBSCRIPTION
    // ==========================================

    @PutMapping("/cancel")
    public ResponseEntity<String> cancelSubscription() {

        return ResponseEntity.ok(

                subscriptionService.cancelSubscription());

    }

    // ==========================================
    // MY SUBSCRIPTION
    // ==========================================

    @GetMapping("/my")
    public ResponseEntity<SubscriptionDTO> getMySubscription() {

        SubscriptionDTO dto =
                subscriptionService.getMySubscription();

        if (dto == null) {

            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(dto);

    }

    // ==========================================
    // CHECK ACTIVE SUBSCRIPTION
    // ==========================================

    @GetMapping("/active")
    public ResponseEntity<Boolean> hasActiveSubscription() {

        return ResponseEntity.ok(

                subscriptionService.hasActiveSubscription());

    }

    // ==========================================
    // CHECK PREMIUM ACCESS
    // ==========================================

    @GetMapping("/premium")
    public ResponseEntity<Boolean> hasPremiumAccess() {

        return ResponseEntity.ok(

                subscriptionService.hasPremiumAccess());

    }

    // ==========================================
    // CHECK VIP ACCESS
    // ==========================================

    @GetMapping("/vip")
    public ResponseEntity<Boolean> hasVipAccess() {

        return ResponseEntity.ok(

                subscriptionService.hasVipAccess());

    }

    // ==========================================
    // UPDATE EXPIRED SUBSCRIPTIONS
    // ==========================================

    @PutMapping("/updateExpired")
    public ResponseEntity<String> updateExpiredSubscriptions() {

        subscriptionService.updateExpiredSubscriptions();

        return ResponseEntity.ok(

                "Expired Subscriptions Updated Successfully");

    }

    // ==========================================
    // ADMIN - GET ALL SUBSCRIPTIONS
    // ==========================================

    @GetMapping("/admin/all")
    public ResponseEntity<List<SubscriptionDTO>> getAllSubscriptions() {

        return ResponseEntity.ok(

                subscriptionService.getAllSubscriptions());

    }

    // ==========================================
    // ADMIN - DELETE SUBSCRIPTION
    // ==========================================

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubscription(
            @PathVariable Long id) {

        subscriptionService.deleteSubscription(id);

        return ResponseEntity.ok(

                "Subscription Deleted Successfully");

    }

    // ==========================================
    // ADMIN - ACTIVATE SUBSCRIPTION
    // ==========================================

    @PutMapping("/admin/activate/{id}")
    public ResponseEntity<String> activateSubscription(
            @PathVariable Long id) {

        return ResponseEntity.ok(

                subscriptionService.activateSubscription(id));

    }

    // ==========================================
    // ADMIN - DEACTIVATE SUBSCRIPTION
    // ==========================================

    @PutMapping("/admin/deactivate/{id}")
    public ResponseEntity<String> deactivateSubscription(
            @PathVariable Long id) {

        return ResponseEntity.ok(

                subscriptionService.deactivateSubscription(id));

    }

}