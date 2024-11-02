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
    void testGetQrCodeReturnsQRCode() {
        String orderId = "order123";
        String expectedQRCode = "QR_CODE_12345";

        when(paymentProcessingUseCase.getQRCode(orderId)).thenReturn(expectedQRCode);

        String actualQRCode = paymentProcessingController.getQRCode(orderId);

        assertEquals(expectedQRCode, actualQRCode);
        verify(paymentProcessingUseCase, times(1)).getQRCode(orderId);
    }

    @Test
    void testGetQrCodeHandlesNullOrderId() {
        String orderId = null;

        when(paymentProcessingUseCase.getQRCode(orderId)).thenReturn(null);

        String actualQRCode = paymentProcessingController.getQRCode(orderId);

        assertNull(actualQRCode);
        verify(paymentProcessingUseCase, times(1)).getQRCode(orderId);
    }

    @Test
    void testGetQrCodeHandlesEmptyOrderId() {
        String orderId = "";

        when(paymentProcessingUseCase.getQRCode(orderId)).thenReturn(null);

        String actualQRCode = paymentProcessingController.getQRCode(orderId);

        assertNull(actualQRCode);
        verify(paymentProcessingUseCase, times(1)).getQRCode(orderId);
    }

    @Test
    void testGetQrCodeHandlesInvalidOrderId() {
        String orderId = "invalid_order_id";

        when(paymentProcessingUseCase.getQRCode(orderId)).thenReturn(null);

        String actualQRCode = paymentProcessingController.getQRCode(orderId);

        assertNull(actualQRCode);
        verify(paymentProcessingUseCase, times(1)).getQRCode(orderId);
    }
}
