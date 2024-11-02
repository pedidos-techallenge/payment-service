package br.com.fiap.techchallenge.payment.core.usecase.out;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentProcessingUseCaseTest {

    @Mock
    private IPaymentGateway paymentGateway;

    @Mock
    private IPaymentRepository paymentRepository;

    private PaymentProcessingUseCase paymentProcessingUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentProcessingUseCase = new PaymentProcessingUseCase(paymentGateway, paymentRepository);
    }

    @Test
    void testGetPaymentStatusReturnsStatus() {
        String orderId = "1234";
        String expectedStatus = "PENDING";

        when(paymentRepository.getPaymentStatus(orderId)).thenReturn(expectedStatus);

        String actualStatus = paymentProcessingUseCase.getPaymentStatus(orderId);

        assertEquals(expectedStatus, actualStatus);
        verify(paymentRepository, times(1)).getPaymentStatus(orderId);
    }

    @Test
    void testCreatePaymentCallsCorrectMethod() {
        String orderId = "1234";
        paymentProcessingUseCase.createPayment(orderId);
        verify(paymentRepository, times(1)).createPayment(orderId);
    }

    @Test
    void testApprovePaymentCallsCorrectMethod() {
        String orderId = "1234";
        when(paymentRepository.getPaymentStatus(orderId)).thenReturn("PENDING");

        paymentProcessingUseCase.approvePayment(orderId, "APPROVED");
        verify(paymentRepository, times(1)).setPaymentStatus(orderId, "APPROVED");
    }

    @Test
    void testApprovePaymentThrowsExceptionWhenOrderNotFound() {
        String orderId = "1234";
        when(paymentRepository.getPaymentStatus(orderId)).thenThrow(new RuntimeException("Order not found"));

        assertThrows(
                RuntimeException.class
                , () -> paymentProcessingUseCase.approvePayment(orderId, "APPROVED")
        );
    }

    @Test
    void testApprovePaymentDoesNotCallSetPaymentStatusWhenOrderStatusIsNotPending() {
        String orderId = "1234";
        when(paymentRepository.getPaymentStatus(orderId)).thenReturn("APPROVED");

        paymentProcessingUseCase.approvePayment(orderId, "APPROVED");
        verify(paymentRepository, never()).setPaymentStatus(orderId, "APPROVED");
    }

    @Test
    void testGetQRCodeCallsCorrectMethods() {
        String orderId = "1234";
        String qrCode = "123456";
        when(paymentRepository.getPaymentQRCode(orderId)).thenReturn(null);
        when(paymentGateway.processQRCodePayment(orderId)).thenReturn(qrCode);

        String actualQRCode = paymentProcessingUseCase.getQRCode(orderId);

        assertEquals(qrCode, actualQRCode);
        verify(paymentRepository, times(1)).setPaymentStatus(orderId, "PENDING", qrCode);
    }

    @Test
    void testGetQRCodeReturnsExistingQRCode() {
        String orderId = "1234";
        String qrCode = "123456";
        when(paymentRepository.getPaymentQRCode(orderId)).thenReturn(qrCode);

        String actualQRCode = paymentProcessingUseCase.getQRCode(orderId);

        assertEquals(qrCode, actualQRCode);
        verify(paymentRepository, never()).setPaymentStatus(orderId, "PENDING", qrCode);
    }

    @Test
    void testGetQRCodeThrowsExceptionWhenGatewayFails() {
        String orderId = "1234";
        when(paymentRepository.getPaymentQRCode(orderId)).thenReturn(null);
        when(paymentGateway.processQRCodePayment(orderId)).thenThrow(new RuntimeException("Gateway failed"));

        assertThrows(
                RuntimeException.class
                , () -> paymentProcessingUseCase.getQRCode(orderId)
        );
    }
}