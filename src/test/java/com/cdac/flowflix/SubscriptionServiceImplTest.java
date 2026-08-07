package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.dto.SubscriptionDTO;
import com.cdac.flowflix.model.Subscription;
import com.cdac.flowflix.model.SubscriptionPlan;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.SubscriptionRepository;
import com.cdac.flowflix.service.SubscriptionService;
import com.cdac.flowflix.service.UserService;
import com.cdac.flowflix.serviceImpl.SubscriptionServiceImpl;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserService userService;

    @Test
    void shouldPurchaseSubscriptionWhenUserExists() {
        User user = new User();
        user.setId(1L);
        SubscriptionPlan plan = SubscriptionPlan.PREMIUM;

        when(userService.getCurrentUser()).thenReturn(user);
        when(subscriptionRepository.findByUser(user)).thenReturn(null);
        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String response = subscriptionService.purchaseSubscription(plan);

        assertEquals("Subscription Purchased Successfully", response);
        verify(subscriptionRepository).save(any(Subscription.class));
    }

    @Test
    void shouldUpgradeSubscriptionIfExists() {
        User user = new User();
        user.setId(1L);
        Subscription subscription = new Subscription();
        subscription.setUser(user);

        when(userService.getCurrentUser()).thenReturn(user);
        when(subscriptionRepository.findByUser(user)).thenReturn(subscription);
        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String response = subscriptionService.upgradeSubscription(SubscriptionPlan.VIP);

        assertEquals("Subscription Upgraded Successfully", response);
        assertEquals(SubscriptionPlan.VIP, subscription.getSubscriptionPlan());
    }

    @Test
    void shouldCancelAndRenewSubscription() {
        User user = new User();
        user.setId(1L);
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionPlan(SubscriptionPlan.PREMIUM);
        subscription.setExpiryDate(LocalDate.now().plusDays(5));
        subscription.setActive(true);

        when(userService.getCurrentUser()).thenReturn(user);
        when(subscriptionRepository.findByUser(user)).thenReturn(subscription);
        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String cancelResponse = subscriptionService.cancelSubscription();
        assertEquals("Subscription Cancelled Successfully", cancelResponse);
        assertFalse(subscription.isActive());

        subscription.setActive(false);
        String renewResponse = subscriptionService.renewSubscription();
        assertEquals("Subscription Renewed Successfully", renewResponse);
        assertTrue(subscription.isActive());
    }

    @Test
    void shouldReturnMySubscriptionAndPrice() {
        User user = new User();
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionPlan(SubscriptionPlan.PREMIUM);

        when(userService.getCurrentUser()).thenReturn(user);
        when(subscriptionRepository.findByUser(user)).thenReturn(subscription);

        SubscriptionDTO dto = subscriptionService.getMySubscription();

        assertNotNull(dto);
        assertEquals(SubscriptionPlan.PREMIUM, subscription.getSubscriptionPlan());
        
        assertEquals(699.0, subscriptionService.getSubscriptionPrice(SubscriptionPlan.PREMIUM));
    }

    @Test
    void shouldReturnFalseForAccessWithoutSubscription() {
        when(userService.getCurrentUser()).thenReturn(null);
        assertFalse(subscriptionService.hasActiveSubscription());
        assertFalse(subscriptionService.hasPremiumAccess());
        assertFalse(subscriptionService.hasVipAccess());
    }
}
