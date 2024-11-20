package br.com.fiap.techchallenge.payment.adapters.controllers;

import br.com.fiap.techchallenge.payment.core.usecase.entities.StatusPayment;
import br.com.fiap.techchallenge.payment.core.usecase.in.IPaymentProcessingUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

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
    void testGetStatusReturnsStatusPayment() {
        String idOrder = "1234";
        StatusPayment expectedStatus = StatusPayment.PENDING;
        when(paymentProcessingUseCase.getStatusPayment(idOrder)).thenReturn(expectedStatus);

        StatusPayment returnedStatus = paymentProcessingController.getStatusPayment(idOrder);

        assertEquals(expectedStatus, returnedStatus);
        verify(paymentProcessingUseCase, times(1)).getStatusPayment(idOrder);
    }


    @Test
    void testCreatePaymentCallsCorrectUseCase() {
        String idOrder = "1234";
        paymentProcessingController.createPayment(idOrder);
        verify(paymentProcessingUseCase, times(1)).createPayment(idOrder);
    }

    @Test
    void testApprovePaymentCallsCorrectUseCase() {
        String idOrder = "1234";
        paymentProcessingController.approvePayment(idOrder, StatusPayment.PAID.name());
        verify(paymentProcessingUseCase, times(1)).approvePayment(idOrder, StatusPayment.PAID);
    }

    @Test
    void testGetQrCodeReturnsQRCode() {
        String idOrder = "order123";
        String expectedQRCode = "QR_CODE_12345";

        when(paymentProcessingUseCase.getQRCode(idOrder)).thenReturn(expectedQRCode);

        String actualQRCode = paymentProcessingController.getQRCode(idOrder);

        assertEquals(expectedQRCode, actualQRCode);
        verify(paymentProcessingUseCase, times(1)).getQRCode(idOrder);
    }

    @Test
    void testGetQrCodeHandlesNullIdOrder() {
        String idOrder = null;

        when(paymentProcessingUseCase.getQRCode(idOrder)).thenReturn(null);

        String actualQRCode = paymentProcessingController.getQRCode(idOrder);

        assertNull(actualQRCode);
        verify(paymentProcessingUseCase, times(1)).getQRCode(idOrder);
    }

    @Test
    void testGetQrCodeHandlesEmptyIdOrder() {
        String idOrder = "";

        when(paymentProcessingUseCase.getQRCode(idOrder)).thenReturn(null);

        String actualQRCode = paymentProcessingController.getQRCode(idOrder);

        assertNull(actualQRCode);
        verify(paymentProcessingUseCase, times(1)).getQRCode(idOrder);
    }

    @Test
    void testGetQrCodeHandlesInvalidIdOrder() {
        String idOrder = "invalid_order_id";

        when(paymentProcessingUseCase.getQRCode(idOrder)).thenReturn(null);

        String actualQRCode = paymentProcessingController.getQRCode(idOrder);

        assertNull(actualQRCode);
        verify(paymentProcessingUseCase, times(1)).getQRCode(idOrder);
    }
}
