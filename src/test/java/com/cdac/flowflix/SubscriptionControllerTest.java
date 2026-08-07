package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cdac.flowflix.controller.SubscriptionController;
import com.cdac.flowflix.dto.SubscriptionDTO;
import com.cdac.flowflix.model.SubscriptionPlan;
import com.cdac.flowflix.service.SubscriptionService;

@ExtendWith(MockitoExtension.class)
class SubscriptionControllerTest {

    @InjectMocks
    private SubscriptionController controller;

    @Mock
    private SubscriptionService subscriptionService;

    @Test
    void shouldHandleSubscriptionOperations() {
        SubscriptionDTO dto = mock(SubscriptionDTO.class);
        when(subscriptionService.purchaseSubscription(SubscriptionPlan.STANDARD)).thenReturn("Purchased");
        when(subscriptionService.upgradeSubscription(SubscriptionPlan.PREMIUM)).thenReturn("Upgraded");
        when(subscriptionService.cancelSubscription()).thenReturn("Cancelled");
        when(subscriptionService.getMySubscription()).thenReturn(dto);
        when(subscriptionService.hasActiveSubscription()).thenReturn(true);
        when(subscriptionService.hasPremiumAccess()).thenReturn(true);
        when(subscriptionService.hasVipAccess()).thenReturn(false);
        when(subscriptionService.getAllSubscriptions()).thenReturn(List.of(dto));

        assertEquals("Purchased", controller.purchaseSubscription(SubscriptionPlan.STANDARD).getBody());
        assertEquals("Upgraded", controller.upgradeSubscription(SubscriptionPlan.PREMIUM).getBody());
        assertEquals("Cancelled", controller.cancelSubscription().getBody());
        assertSame(dto, controller.getMySubscription().getBody());
        assertTrue(controller.hasActiveSubscription().getBody());
        assertTrue(controller.hasPremiumAccess().getBody());
        assertFalse(controller.hasVipAccess().getBody());
        assertEquals(1, controller.getAllSubscriptions().getBody().size());
    }

    @Test
    void shouldReturnNotFoundWhenSubscriptionMissing() {
        when(subscriptionService.getMySubscription()).thenReturn(null);

        ResponseEntity<?> response = controller.getMySubscription();

        assertEquals(404, response.getStatusCodeValue());
    }
}
