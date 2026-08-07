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

import com.cdac.flowflix.controller.PaymentController;
import com.cdac.flowflix.dto.PaymentDTO;
import com.cdac.flowflix.service.PaymentService;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @InjectMocks
    private PaymentController controller;

    @Mock
    private PaymentService paymentService;

    @Test
    void shouldCreatePaymentAndVerifyRevenue() {
        PaymentDTO paymentDTO = mock(PaymentDTO.class);
        when(paymentService.createPayment(1L)).thenReturn(paymentDTO);
        when(paymentService.verifyPayment(1L)).thenReturn("Verified");
        when(paymentService.getMyPayments()).thenReturn(List.of(paymentDTO));
        when(paymentService.getAllPayments()).thenReturn(List.of(paymentDTO));
        when(paymentService.getTotalRevenue()).thenReturn(100L);
        when(paymentService.getMonthlyRevenue()).thenReturn(50L);
        when(paymentService.getTodayRevenue()).thenReturn(10L);

        assertSame(paymentDTO, controller.createPayment(1L).getBody());
        assertEquals("Verified", controller.verifyPayment(1L).getBody());
        assertEquals(1, controller.getMyPayments().getBody().size());
        assertEquals(1, controller.getAllPayments().getBody().size());
        assertEquals(100L, controller.getTotalRevenue().getBody());
        assertEquals(50L, controller.getMonthlyRevenue().getBody());
        assertEquals(10L, controller.getTodayRevenue().getBody());
    }

    @Test
    void shouldReturnNotFoundWhenCreatePaymentFails() {
        when(paymentService.createPayment(2L)).thenReturn(null);

        ResponseEntity< ?> response = controller.createPayment(2L);

        assertEquals(404, response.getStatusCodeValue());
    }
}
