/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.SubscriptionDTO;
import com.cdac.flowflix.model.Subscription;
import com.cdac.flowflix.model.SubscriptionPlan;

public interface SubscriptionService {

    // ==========================================
    // BASIC CRUD
    // ==========================================

    Subscription save(Subscription subscription);

    Subscription findById(Long id);

    List<SubscriptionDTO> getAllSubscriptions();

    SubscriptionDTO getMySubscription();

    Subscription getSubscription();

    // ==========================================
    // SUBSCRIPTION MANAGEMENT

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

    // ==========================================

    String purchaseSubscription(SubscriptionPlan plan);

    String upgradeSubscription(SubscriptionPlan plan);

    String cancelSubscription();

    String renewSubscription();

    String activateSubscription(Long id);

    String deactivateSubscription(Long id);

    void deleteSubscription(Long id);

    void updateExpiredSubscriptions();

    boolean hasActiveSubscription();

    boolean hasPremiumAccess();

    boolean hasVipAccess();

    // ==========================================
    // PRICE
    // ==========================================

    Double getSubscriptionPrice(SubscriptionPlan plan);

}
