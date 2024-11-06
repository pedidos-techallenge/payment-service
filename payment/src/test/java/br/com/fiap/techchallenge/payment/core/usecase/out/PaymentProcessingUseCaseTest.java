package br.com.fiap.techchallenge.payment.core.usecase.out;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderStatus;
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
        OrderPayment testOrderPayment = new OrderPayment("1234", OrderStatus.PENDING, null);

        when(paymentRepository.getPayment(testOrderPayment.getOrderId())).thenReturn(testOrderPayment);

        OrderStatus actualStatus = paymentProcessingUseCase.getPaymentStatus(testOrderPayment.getOrderId());

        assertEquals(testOrderPayment.getOrderStatus(), actualStatus);
        verify(paymentRepository, times(1)).getPayment(testOrderPayment.getOrderId());
    }


    @Test
    void testCreatePaymentCallsCorrectMethod() {
        String orderId = "1234";
        paymentProcessingUseCase.createPayment(orderId);
        verify(paymentRepository, times(1)).createPayment(any(OrderPayment.class));
    }

    @Test
    void testApprovePaymentCallsCorrectMethod() {
        OrderPayment testOrderPayment = new OrderPayment("1234", OrderStatus.PENDING, null);

        when(paymentRepository.getPayment(testOrderPayment.getOrderId())).thenReturn(testOrderPayment);

        paymentProcessingUseCase.approvePayment(testOrderPayment.getOrderId(), OrderStatus.APPROVED);
        verify(paymentRepository, times(1)).updatePayment(testOrderPayment);
    }

    @Test
    void testApprovePaymentThrowsExceptionWhenOrderNotFound() {
        String orderId = "1234";
        when(paymentRepository.getPayment(orderId)).thenThrow(new RuntimeException("Order not found"));

        assertThrows(
                RuntimeException.class
                , () -> paymentProcessingUseCase.approvePayment(orderId, OrderStatus.APPROVED)
        );
    }

    @Test
    void testGetQRCodeCallsCorrectMethods() {
        OrderPayment testOrderPayment = new OrderPayment("1234", OrderStatus.CREATED, null);
        when(paymentRepository.getPayment(testOrderPayment.getOrderId())).thenReturn(testOrderPayment);

        String qrCode = "123456";
        when(paymentGateway.processQRCodePayment(testOrderPayment.getOrderId())).thenReturn(qrCode);

        String actualQRCode = paymentProcessingUseCase.getQRCode(testOrderPayment.getOrderId());

        assertEquals(qrCode, actualQRCode);
        verify(paymentRepository, times(1)).updatePayment(testOrderPayment);
    }

    @Test
    void testGetQRCodeReturnsExistingQRCode() {
        OrderPayment testOrderPayment = new OrderPayment("1234", OrderStatus.PENDING, "QR_CODE_123456");
        when(paymentRepository.getPayment(testOrderPayment.getOrderId())).thenReturn(testOrderPayment);

        String actualQRCode = paymentProcessingUseCase.getQRCode(testOrderPayment.getOrderId());

        assertEquals(testOrderPayment.getQrCode(), actualQRCode);
        verify(paymentRepository, never()).updatePayment(testOrderPayment);
    }

    @Test
    void testGetQRCodeThrowsExceptionWhenGatewayFails() {
        OrderPayment testOrderPayment = new OrderPayment("1234", OrderStatus.CREATED, null);
        when(paymentRepository.getPayment(testOrderPayment.getOrderId())).thenReturn(testOrderPayment);

        when(paymentRepository.getPayment(testOrderPayment.getOrderId())).thenReturn(null);
        when(paymentGateway.processQRCodePayment(testOrderPayment.getOrderId())).thenThrow(new RuntimeException("Gateway failed"));

        assertThrows(
                RuntimeException.class
                , () -> paymentProcessingUseCase.getQRCode(testOrderPayment.getOrderId())
        );
    }
}