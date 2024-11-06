package br.com.fiap.techchallenge.payment.infrastructure.api;

import br.com.fiap.techchallenge.payment.adapters.controllers.PaymentProcessingController;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderStatus;
import br.com.fiap.techchallenge.payment.infrastructure.dto.OrderRequestDTO;
import br.com.fiap.techchallenge.payment.infrastructure.dto.PaymentStatusDTO;
import br.com.fiap.techchallenge.payment.infrastructure.dto.QRCodeResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentProcessingServiceTest {

    @Mock
    private PaymentProcessingController paymentProcessingController;

    private PaymentProcessingService paymentProcessingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentProcessingService = new PaymentProcessingService(paymentProcessingController);
    }

    @Test
    void testCreatePaymentReturnsOKWhenSucessfull() {
        String orderId = "1234";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(orderId, null);

        int responseStatus = paymentProcessingService.createPayment(orderRequestDTO).getStatusCode().value();

        assertEquals(200, responseStatus);
        verify(paymentProcessingController, times(1)).createPayment(orderId);
    }

    @Test
    void testCreatePaymentReturns500WhenUnsucessfull() {
        String orderId = "1234";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(orderId, null);

        doThrow(new RuntimeException()).when(paymentProcessingController).createPayment(orderId);
        int responseStatus = paymentProcessingService.createPayment(orderRequestDTO).getStatusCode().value();

        assertEquals(500, responseStatus);
        verify(paymentProcessingController, times(1)).createPayment(orderId);
    }

    @Test
    void testApprovePaymentReturnsOKWhenSucessfull() {
        String orderId = "1234";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(orderId, OrderStatus.APPROVED.name());

        int responseStatus = paymentProcessingService.approvePayment(orderRequestDTO).getStatusCode().value();

        assertEquals(200, responseStatus);
        verify(paymentProcessingController, times(1)).approvePayment(orderId, OrderStatus.APPROVED.name());
    }

    @Test
    void testApprovePaymentReturns500WhenUnsucessfull() {
        String orderId = "1234";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(orderId, OrderStatus.APPROVED.name());

        doThrow(new RuntimeException()).when(paymentProcessingController).approvePayment(orderId, OrderStatus.APPROVED.name());
        int responseStatus = paymentProcessingService.approvePayment(orderRequestDTO).getStatusCode().value();

        assertEquals(500, responseStatus);
        verify(paymentProcessingController, times(1)).approvePayment(orderId, OrderStatus.APPROVED.name());
    }

    @Test
    void testGetQRCodeReturnsCodeSucessfully() {
        String orderId = "1234";
        String expectedQRCode = "QR_CODE_12345";
        QRCodeResponseDTO qrCodeResponseDTO = new QRCodeResponseDTO(orderId, expectedQRCode);

        when(paymentProcessingController.getQRCode(orderId)).thenReturn(expectedQRCode);
        QRCodeResponseDTO actualQRCodeResponse = (QRCodeResponseDTO) paymentProcessingService.getQRCode(orderId).getBody();

        assertEquals(qrCodeResponseDTO.qrCode(), actualQRCodeResponse.qrCode());
        assertEquals(qrCodeResponseDTO.orderId(), actualQRCodeResponse.orderId());
        verify(paymentProcessingController, times(1)).getQRCode(orderId);
    }

    @Test
    void testGetQRCodeHandlesNullOrderId() {
        String orderId = null;

        when(paymentProcessingController
                .getQRCode(orderId))
                .thenReturn(null);
        int responseStatus = paymentProcessingService.getQRCode(orderId).getStatusCode().value();

        assertEquals(404, responseStatus);
        verify(paymentProcessingController, times(1)).getQRCode(orderId);
    }

    @Test
    void testGetQRCodeHandlesInvalidOrderId() {
        String orderId = "";

        when(paymentProcessingController
                .getQRCode(orderId))
                .thenReturn(null);
        int responseStatus = paymentProcessingService.getQRCode(orderId).getStatusCode().value();

        assertEquals(404, responseStatus);
        verify(paymentProcessingController, times(1)).getQRCode(orderId);
    }

    @Test
    void testGetQRCodeHandlesInternalServerError() {
        String orderId = "1234";

        when(paymentProcessingController.getQRCode(orderId)).thenThrow(new RuntimeException());
        int responseStatus = paymentProcessingService.getQRCode(orderId).getStatusCode().value();

        assertEquals(500, responseStatus);
        verify(paymentProcessingController, times(1)).getQRCode(orderId);
    }

    @Test
    void testGetPaymentStatusReturnsStatusSuccessfully() {
        String orderId = "1234";
        when(paymentProcessingController.getPaymentStatus(orderId)).thenReturn(OrderStatus.PENDING);
        PaymentStatusDTO paymentStatus = (PaymentStatusDTO) paymentProcessingService.getPaymentStatus(orderId).getBody();
        assertEquals(paymentStatus.orderId(), orderId);
        assertEquals(paymentStatus.orderStatus(), OrderStatus.PENDING.name());
    }

    @Test
    void testGetPaymentStatusHandlesNullOrderId() {
        String orderId = null;

        when(paymentProcessingController
                .getPaymentStatus(orderId))
                .thenReturn(null);
        int responseStatus = paymentProcessingService.getPaymentStatus(orderId).getStatusCode().value();

        assertEquals(404, responseStatus);
        verify(paymentProcessingController, times(1)).getPaymentStatus(orderId);
    }

    @Test
    void testGetPaymentStatusHandlesInvalidOrderId() {
        String orderId = "";

        when(paymentProcessingController
                .getPaymentStatus(orderId))
                .thenReturn(null);
        int responseStatus = paymentProcessingService.getPaymentStatus(orderId).getStatusCode().value();

        assertEquals(404, responseStatus);
        verify(paymentProcessingController, times(1)).getPaymentStatus(orderId);
    }

    @Test
    void testGetPaymentStatusHandlesInternalServerError() {
        String orderId = "1234";

        when(paymentProcessingController.getPaymentStatus(orderId)).thenThrow(new RuntimeException());
        int responseStatus = paymentProcessingService.getPaymentStatus(orderId).getStatusCode().value();

        assertEquals(500, responseStatus);
        verify(paymentProcessingController, times(1)).getPaymentStatus(orderId);
    }
}

