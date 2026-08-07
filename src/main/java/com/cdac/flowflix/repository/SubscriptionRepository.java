package com.cdac.flowflix.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.flowflix.model.Subscription;
import com.cdac.flowflix.model.SubscriptionPlan;
import com.cdac.flowflix.model.User;

@Repository
public interface SubscriptionRepository
        extends JpaRepository<Subscription, Long> {

    // ==========================================
    // FIND USER SUBSCRIPTION
    // ==========================================

    Subscription findByUser(User user);

    // ==========================================
    // FIND ACTIVE SUBSCRIPTION
    // ==========================================

    Subscription findByUserAndActiveTrue(User user);

    // ==========================================
    // FIND BY SUBSCRIPTION PLAN
    // ==========================================

    List<Subscription> findBySubscriptionPlan(
            SubscriptionPlan subscriptionPlan);

    // ==========================================
    // FIND ACTIVE SUBSCRIPTIONS
    // ==========================================

    List<Subscription> findByActiveTrue();

    // ==========================================
    // FIND EXPIRED SUBSCRIPTIONS
    // ==========================================

    List<Subscription> findByExpiryDateBefore(
            LocalDate date);

    // ==========================================
    // COUNT ACTIVE SUBSCRIPTIONS
    // ==========================================

    long countByActiveTrue();

    // ==========================================
    // COUNT BY SUBSCRIPTION PLAN
    // ==========================================

    long countBySubscriptionPlan(
            SubscriptionPlan subscriptionPlan);

}