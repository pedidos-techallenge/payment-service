package br.com.fiap.techchallenge.payment.core.usecase.out;

import br.com.fiap.techchallenge.payment.adapters.gateways.IMessagePublisher;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.StatusPayment;
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

    @Mock
    private IMessagePublisher messagePublisher;

    private PaymentProcessingUseCase paymentProcessingUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentProcessingUseCase = new PaymentProcessingUseCase(paymentGateway, paymentRepository, messagePublisher);
    }

    @Test
    void testGetStatusReturnsStatusPayment() {
        OrderPayment testOrderPayment = new OrderPayment("1234", StatusPayment.PENDING, null);

        when(paymentRepository.getPayment(testOrderPayment.getIdOrder())).thenReturn(testOrderPayment);

        StatusPayment actualStatus = paymentProcessingUseCase.getStatusPayment(testOrderPayment.getIdOrder());

        assertEquals(testOrderPayment.getStatusPayment(), actualStatus);
        verify(paymentRepository, times(1)).getPayment(testOrderPayment.getIdOrder());
    }


    @Test
    void testCreatePaymentCallsCorrectMethod() {
        String idOrder = "1234";
        paymentProcessingUseCase.createPayment(idOrder);
        verify(paymentRepository, times(1)).createPayment(any(OrderPayment.class));
    }

    @Test
    void testApprovePaymentCallsCorrectMethod() {
        OrderPayment testOrderPayment = new OrderPayment("1234", StatusPayment.PENDING, null);

        when(paymentRepository.getPayment(testOrderPayment.getIdOrder())).thenReturn(testOrderPayment);

        paymentProcessingUseCase.approvePayment(testOrderPayment.getIdOrder(), StatusPayment.PAID);
        verify(paymentRepository, times(1)).updatePayment(testOrderPayment);
    }

    @Test
    void testApprovePaymentThrowsExceptionWhenOrderNotFound() {
        String idOrder = "1234";
        when(paymentRepository.getPayment(idOrder)).thenThrow(new RuntimeException("Order not found"));

        assertThrows(
                RuntimeException.class
                , () -> paymentProcessingUseCase.approvePayment(idOrder, StatusPayment.PAID)
        );
    }

    @Test
    void testGetQRCodeCallsCorrectMethods() {
        OrderPayment testOrderPayment = new OrderPayment("1234", StatusPayment.CREATED, null);
        when(paymentRepository.getPayment(testOrderPayment.getIdOrder())).thenReturn(testOrderPayment);

        String qrCode = "123456";
        when(paymentGateway.processQRCodePayment(testOrderPayment.getIdOrder())).thenReturn(qrCode);

        String actualQRCode = paymentProcessingUseCase.getQRCode(testOrderPayment.getIdOrder());

        assertEquals(qrCode, actualQRCode);
        verify(paymentRepository, times(1)).updatePayment(testOrderPayment);
    }

    @Test
    void testGetQRCodeReturnsExistingQRCode() {
        OrderPayment testOrderPayment = new OrderPayment("1234", StatusPayment.PENDING, "QR_CODE_123456");
        when(paymentRepository.getPayment(testOrderPayment.getIdOrder())).thenReturn(testOrderPayment);

        String actualQRCode = paymentProcessingUseCase.getQRCode(testOrderPayment.getIdOrder());

        assertEquals(testOrderPayment.getQrCode(), actualQRCode);
        verify(paymentRepository, never()).updatePayment(testOrderPayment);
    }

    @Test
    void testGetQRCodeThrowsExceptionWhenGatewayFails() {
        OrderPayment testOrderPayment = new OrderPayment("1234", StatusPayment.CREATED, null);
        when(paymentRepository.getPayment(testOrderPayment.getIdOrder())).thenReturn(testOrderPayment);

        when(paymentRepository.getPayment(testOrderPayment.getIdOrder())).thenReturn(null);
        when(paymentGateway.processQRCodePayment(testOrderPayment.getIdOrder())).thenThrow(new RuntimeException("Gateway failed"));

        assertThrows(
                RuntimeException.class
                , () -> paymentProcessingUseCase.getQRCode(testOrderPayment.getIdOrder())
        );
    }
}