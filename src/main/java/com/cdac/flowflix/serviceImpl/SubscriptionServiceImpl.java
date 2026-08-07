package com.cdac.flowflix.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.SubscriptionDTO;
import com.cdac.flowflix.model.Subscription;
import com.cdac.flowflix.model.SubscriptionPlan;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.SubscriptionRepository;
import com.cdac.flowflix.service.SubscriptionService;
import com.cdac.flowflix.service.UserService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserService userService;

    // ==========================================
    // SAVE
    // ==========================================

    @Override
    public Subscription save(Subscription subscription) {

        return subscriptionRepository.save(subscription);

    }

    // ==========================================
    // FIND BY ID
    // ==========================================

    @Override
    public Subscription findById(Long id) {

        return subscriptionRepository
                .findById(id)
                .orElse(null);

    }

    // ==========================================
    // PURCHASE SUBSCRIPTION
    // ==========================================

    @Override
    public String purchaseSubscription(SubscriptionPlan plan) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Found";

        }

        Subscription subscription =
                subscriptionRepository.findByUser(user);

        if (subscription == null) {

            subscription = new Subscription();

            subscription.setUser(user);

        }

        subscription.setSubscriptionPlan(plan);

        subscription.setStartDate(LocalDate.now());

        subscription.setExpiryDate(
                LocalDate.now().plusDays(plan.getValidityDays()));

        subscription.setActive(true);

        subscriptionRepository.save(subscription);

        return "Subscription Purchased Successfully";

    }

    // ==========================================
    // UPGRADE SUBSCRIPTION
    // ==========================================

    @Override
    public String upgradeSubscription(SubscriptionPlan plan) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Found";

        }

        Subscription subscription =
                subscriptionRepository.findByUser(user);

        if (subscription == null) {

            return purchaseSubscription(plan);

        }

        subscription.setSubscriptionPlan(plan);

        subscription.setStartDate(LocalDate.now());

        subscription.setExpiryDate(
                LocalDate.now().plusDays(plan.getValidityDays()));

        subscription.setActive(true);

        subscriptionRepository.save(subscription);

        return "Subscription Upgraded Successfully";

    }

    // ==========================================
    // CANCEL SUBSCRIPTION
    // ==========================================

    @Override
    public String cancelSubscription() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Found";

        }

        Subscription subscription =
                subscriptionRepository.findByUser(user);

        if (subscription == null) {

            return "Subscription Not Found";

        }

        subscription.setActive(false);

        subscriptionRepository.save(subscription);

        return "Subscription Cancelled Successfully";

    }

    // ==========================================
    // RENEW SUBSCRIPTION
    // ==========================================

    @Override
    public String renewSubscription() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Found";

        }

        Subscription subscription =
                subscriptionRepository.findByUser(user);

        if (subscription == null) {

            return "Subscription Not Found";

        }

        subscription.setStartDate(LocalDate.now());

        subscription.setExpiryDate(
                LocalDate.now().plusDays(
                        subscription.getSubscriptionPlan().getValidityDays()));

        subscription.setActive(true);

        subscriptionRepository.save(subscription);

        return "Subscription Renewed Successfully";

    }

    // ==========================================
    // DELETE SUBSCRIPTION

    @Override
    public void deleteSubscription(Long id) {

        subscriptionRepository.deleteById(id);

    }

    // ==========================================
    // ACTIVATE SUBSCRIPTION

    @Override
    public String activateSubscription(Long id) {

        Subscription subscription =
                subscriptionRepository.findById(id)
                        .orElse(null);

        if (subscription == null) {
            return "Subscription Not Found";
        }

        subscription.setActive(true);
        subscriptionRepository.save(subscription);

        return "Subscription Activated Successfully";

    }

    // ==========================================
    // DEACTIVATE SUBSCRIPTION

    @Override
    public String deactivateSubscription(Long id) {

        Subscription subscription =
                subscriptionRepository.findById(id)
                        .orElse(null);

        if (subscription == null) {
            return "Subscription Not Found";
        }

        subscription.setActive(false);
        subscriptionRepository.save(subscription);

        return "Subscription Deactivated Successfully";

    }

    // ==========================================
    // GET MY SUBSCRIPTION
    // ==========================================

    @Override
    public SubscriptionDTO getMySubscription() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return null;

        }

        Subscription subscription =
                subscriptionRepository.findByUser(user);

        if (subscription == null) {

            return null;

        }

        return new SubscriptionDTO(subscription);

    }

    // ==========================================
    // GET SUBSCRIPTION ENTITY
    // ==========================================

    @Override
    public Subscription getSubscription() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return null;

        }

        return subscriptionRepository.findByUser(user);

    }

    // ==========================================
    // ACTIVE SUBSCRIPTION
    // ==========================================

    @Override
    public boolean hasActiveSubscription() {

        Subscription subscription = getSubscription();

        if (subscription == null) {

            return false;

        }

        if (!subscription.isActive()) {

            return false;

        }

        return subscription.getExpiryDate() != null
                &&
                !subscription.getExpiryDate()
                        .isBefore(LocalDate.now());

    }

    // ==========================================
    // PREMIUM ACCESS
    // ==========================================

    @Override
    public boolean hasPremiumAccess() {

        Subscription subscription = getSubscription();

        if (subscription == null) {

            return false;

        }

        if (!hasActiveSubscription()) {

            return false;

        }

        return subscription.getSubscriptionPlan()
                == SubscriptionPlan.PREMIUM
                ||
                subscription.getSubscriptionPlan()
                == SubscriptionPlan.VIP;

    }

    // ==========================================
    // VIP ACCESS
    // ==========================================

    @Override
    public boolean hasVipAccess() {

        Subscription subscription = getSubscription();

        if (subscription == null) {

            return false;

        }

        if (!hasActiveSubscription()) {

            return false;

        }

        return subscription.getSubscriptionPlan()
                == SubscriptionPlan.VIP;

    }

    // ==========================================
    // UPDATE EXPIRED SUBSCRIPTIONS
    // ==========================================

    @Override
    public void updateExpiredSubscriptions() {

        List<Subscription> subscriptions =
                subscriptionRepository.findByActiveTrue();

        for (Subscription subscription : subscriptions) {

            if (subscription.getExpiryDate() != null
                    &&
                    subscription.getExpiryDate()
                            .isBefore(LocalDate.now())) {

                subscription.setActive(false);

                subscriptionRepository.save(subscription);

            }

        }

    }

    // ==========================================
    // GET ALL SUBSCRIPTIONS
    // ==========================================

    @Override
    public List<SubscriptionDTO> getAllSubscriptions() {

        return subscriptionRepository
                .findAll()
                .stream()
                .map(SubscriptionDTO::new)
                .collect(Collectors.toList());

    }

    // ==========================================
    // SUBSCRIPTION PRICE
    // ==========================================

    @Override
    public Double getSubscriptionPrice(
            SubscriptionPlan plan) {

        if (plan == null) {

            return 0.0;

        }

        return plan.getPrice();

    }

}