package com.cdac.flowflix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.SubscriptionDTO;
import com.cdac.flowflix.model.Subscription;
import com.cdac.flowflix.service.SubscriptionService;

@RestController
@RequestMapping("/api/subscription")
@CrossOrigin("*")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<Subscription> subscribe(
            @RequestBody SubscriptionDTO dto) {

        return ResponseEntity.ok(
                subscriptionService.subscribe(dto));
    }

    @GetMapping("/{username}")
    public ResponseEntity<Subscription> getSubscription(
            @PathVariable String username) {

        return ResponseEntity.ok(
                subscriptionService.getSubscription(username));
    }

    @GetMapping("/active/{username}")
    public ResponseEntity<Boolean> hasSubscription(
            @PathVariable String username) {

        return ResponseEntity.ok(
                subscriptionService.hasActiveSubscription(username));
    }
}