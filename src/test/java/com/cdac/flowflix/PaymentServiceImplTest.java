package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.dto.PaymentDTO;
import com.cdac.flowflix.model.Payment;
import com.cdac.flowflix.model.PaymentStatus;
import com.cdac.flowflix.model.Subscription;
import com.cdac.flowflix.model.SubscriptionPlan;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.PaymentRepository;
import com.cdac.flowflix.repository.SubscriptionRepository;
import com.cdac.flowflix.service.SubscriptionService;
import com.cdac.flowflix.service.UserService;
import com.cdac.flowflix.serviceImpl.PaymentServiceImpl;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserService userService;

    @Mock
    private SubscriptionService subscriptionService;

    @Test
    void shouldReturnNullWhenSubscriptionMissing() {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.empty());

        assertNull(paymentService.createPayment(1L));
    }

    @Test
    void shouldCreatePaymentSuccessfully() {
        User user = new User();
        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setUser(user);
        subscription.setSubscriptionPlan(SubscriptionPlan.PREMIUM);

        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));
        when(subscriptionService.getSubscriptionPrice(SubscriptionPlan.PREMIUM)).thenReturn(100.0);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PaymentDTO dto = paymentService.createPayment(1L);

        assertNotNull(dto);
        assertEquals("INR", dto.getCurrency());
    }

    @Test
    void shouldVerifyPaymentSuccessfully() {
        Payment payment = new Payment();
        payment.setId(1L);
        Subscription subscription = new Subscription();
        payment.setSubscription(subscription);

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String response = paymentService.verifyPayment(1L);

        assertEquals("Payment Verified Successfully", response);
        assertEquals(PaymentStatus.SUCCESS, payment.getPaymentStatus());
    }

    @Test
    void shouldReturnZeroRevenueWhenNoPayments() {
        
        when(paymentRepository.findByPaymentStatus(PaymentStatus.SUCCESS)).thenReturn(List.of());

        assertEquals(0L, paymentService.getTotalRevenue());
        assertEquals(0L, paymentService.getMonthlyRevenue());
        assertEquals(0L, paymentService.getTodayRevenue());
    }
}
