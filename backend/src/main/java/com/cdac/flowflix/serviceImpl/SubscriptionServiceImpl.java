package com.cdac.flowflix.serviceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.SubscriptionDTO;
import com.cdac.flowflix.model.Subscription;
import com.cdac.flowflix.repository.SubscriptionRepository;
import com.cdac.flowflix.service.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository repository;

    @Override
    public Subscription subscribe(SubscriptionDTO dto) {

        Subscription subscription =
                repository.findByUsername(dto.getUsername());

        if (subscription == null) {

            subscription = new Subscription();
            subscription.setUsername(dto.getUsername());

        }

        subscription.setPlan(dto.getPlan());
        subscription.setActive(true);

        LocalDate today = LocalDate.now();

        subscription.setStartDate(today);
        subscription.setExpiryDate(today.plusMonths(1));

        return repository.save(subscription);

    }

    @Override
    public Subscription getSubscription(String username) {

        return repository.findByUsername(username);

    }

    @Override
    public boolean hasActiveSubscription(String username) {

        Subscription subscription =
                repository.findByUsername(username);

        if (subscription == null) {
            return false;
        }

        if (!subscription.isActive()) {
            return false;
        }

        if (subscription.getExpiryDate() == null) {
            return false;
        }

        return subscription.getExpiryDate().isAfter(LocalDate.now());

    }
}