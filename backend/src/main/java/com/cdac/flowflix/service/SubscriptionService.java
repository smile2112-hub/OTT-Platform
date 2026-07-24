package com.cdac.flowflix.service;

import com.cdac.flowflix.dto.SubscriptionDTO;
import com.cdac.flowflix.model.Subscription;

public interface SubscriptionService {

    Subscription subscribe(SubscriptionDTO dto);

    Subscription getSubscription(String username);

    boolean hasActiveSubscription(String username);

}