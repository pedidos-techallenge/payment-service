package br.com.fiap.techchallenge.payment.adapters.controllers;

import br.com.fiap.techchallenge.payment.core.usecase.in.IPaymentProcessingUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Assertions.*;

public class PaymentProcessingControllerTest {

    @Mock
    private IPaymentProcessingUseCase paymentProcessingUseCase;

    private PaymentProcessingController paymentProcessingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentProcessingController = new PaymentProcessingController(paymentProcessingUseCase);
    }

    @Test
    void processPaymentReturnsQRCode() {
        String orderId = "order123";
        String expectedQRCode = "QR_CODE_12345";

        when(paymentProcessingUseCase.processPayment(orderId)).thenReturn(expectedQRCode);

        String actualQRCode = paymentProcessingController.processPayment(orderId);

        assertEquals(expectedQRCode, actualQRCode);
        verify(paymentProcessingUseCase, times(1)).processPayment(orderId);
    }

    @Test
    void processPaymentHandlesNullOrderId() {
        String orderId = null;

        when(paymentProcessingUseCase.processPayment(orderId)).thenReturn(null);

        String actualQRCode = paymentProcessingController.processPayment(orderId);

        assertNull(actualQRCode);
        verify(paymentProcessingUseCase, times(1)).processPayment(orderId);
    }

    @Test
    void processPaymentHandlesEmptyOrderId() {
        String orderId = "";

        when(paymentProcessingUseCase.processPayment(orderId)).thenReturn(null);

        String actualQRCode = paymentProcessingController.processPayment(orderId);

        assertNull(actualQRCode);
        verify(paymentProcessingUseCase, times(1)).processPayment(orderId);
    }

    @Test
    void processPaymentHandlesInvalidOrderId() {
        String orderId = "invalid_order_id";

        when(paymentProcessingUseCase.processPayment(orderId)).thenReturn(null);

        String actualQRCode = paymentProcessingController.processPayment(orderId);

        assertNull(actualQRCode);
        verify(paymentProcessingUseCase, times(1)).processPayment(orderId);
    }
}
