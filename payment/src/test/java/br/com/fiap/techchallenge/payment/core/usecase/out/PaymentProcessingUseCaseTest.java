package br.com.fiap.techchallenge.payment.core.usecase.out;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentProcessingUseCaseTest {

    @Mock
    private IPaymentGateway paymentGateway;

    private PaymentProcessingUseCase paymentProcessingUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentProcessingUseCase = new PaymentProcessingUseCase(paymentGateway);
    }

    @Test
    void processPaymentReturnsQRCode() {
        String orderId = "order123";
        String expectedQRCode = "QR_CODE_12345";

        when(paymentGateway.processQRCodePayment(orderId)).thenReturn(expectedQRCode);

        String actualQRCode = paymentProcessingUseCase.processPayment(orderId);

        assertEquals(expectedQRCode, actualQRCode);
        verify(paymentGateway, times(1)).processQRCodePayment(orderId);
    }

    @Test
    void processPaymentHandlesNullOrderId() {
        String orderId = null;

        when(paymentGateway.processQRCodePayment(orderId)).thenReturn(null);
        String actualQRCode = paymentProcessingUseCase.processPayment(orderId);

        assertNull(actualQRCode);
        verify(paymentGateway, times(1)).processQRCodePayment(orderId);
    }

    @Test
    void processPaymentHandlesEmptyOrderId() {
        String orderId = "";

        when(paymentGateway.processQRCodePayment(orderId)).thenReturn(null);
        String actualQRCode = paymentProcessingUseCase.processPayment(orderId);

        assertNull(actualQRCode);
        verify(paymentGateway, times(1)).processQRCodePayment(orderId);
    }

    @Test
    void processPaymentHandlesInvalidOrderId() {
        String orderId = "invalid_order";

        when(paymentGateway.processQRCodePayment(orderId)).thenReturn(null);
        String actualQRCode = paymentProcessingUseCase.processPayment(orderId);

        assertNull(actualQRCode);
        verify(paymentGateway, times(1)).processQRCodePayment(orderId);
    }
}