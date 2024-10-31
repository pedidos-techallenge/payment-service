package br.com.fiap.techchallenge.payment.infrastructure.api;

import br.com.fiap.techchallenge.payment.adapters.controllers.PaymentProcessingController;
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
    void processPaymentReturnsQRCode() {
        String orderId = "order123";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(orderId);
        String expectedQRCode = "QR_CODE_12345";
        QRCodeResponseDTO qrCodeResponseDTO = new QRCodeResponseDTO(orderId, expectedQRCode);

        when(paymentProcessingController.processPayment(orderId)).thenReturn(expectedQRCode);
        QRCodeResponseDTO actualQRCodeResponse = (QRCodeResponseDTO) paymentProcessingService.getPaymentCode(orderRequestDTO).getBody();

        assertEquals(qrCodeResponseDTO.qrCode(), actualQRCodeResponse.qrCode());
        assertEquals(qrCodeResponseDTO.orderId(), actualQRCodeResponse.orderId());
        verify(paymentProcessingController, times(1)).processPayment(orderId);
    }

    @Test
    void processPaymentHandlesNullOrderId() {
        String orderId = null;
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(orderId);

        when(paymentProcessingController.processPayment(orderId)).thenReturn(null);
        QRCodeResponseDTO actualQRCodeResponse = (QRCodeResponseDTO) paymentProcessingService.getPaymentCode(orderRequestDTO).getBody();

        assertNull(actualQRCodeResponse.orderId());
        assertNull(actualQRCodeResponse.qrCode());
        verify(paymentProcessingController, times(1)).processPayment(orderId);
    }

    @Test
    void processPaymentHandlesEmptyOrderId() {
        String orderId = "";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(orderId);

        when(paymentProcessingController.processPayment(orderId)).thenReturn(null);
        QRCodeResponseDTO actualQRCodeResponse = (QRCodeResponseDTO) paymentProcessingService.getPaymentCode(orderRequestDTO).getBody();

        assertEquals(orderId, actualQRCodeResponse.orderId());
        assertNull(actualQRCodeResponse.qrCode());
        verify(paymentProcessingController, times(1)).processPayment(orderId);
    }

    @Test
    void processPaymentHandlesInvalidOrderId() {
        String orderId = "invalid_order";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(orderId);

        when(paymentProcessingController.processPayment(orderId)).thenReturn(null);
        QRCodeResponseDTO actualQRCodeResponse = (QRCodeResponseDTO) paymentProcessingService.getPaymentCode(orderRequestDTO).getBody();

        assertEquals(orderId, actualQRCodeResponse.orderId());
        assertNull(actualQRCodeResponse.qrCode());
        verify(paymentProcessingController, times(1)).processPayment(orderId);
    }

    @Test
    void processPaymentHandlesInternalServerError() {
        String orderId = "order123";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(orderId);

        when(paymentProcessingController.processPayment(orderId)).thenThrow(new RuntimeException());
        String actualQRCodeResponse = (String) paymentProcessingService.getPaymentCode(orderRequestDTO).getBody();

        assertEquals("Erro ao gerar código de pagamento", actualQRCodeResponse);
        verify(paymentProcessingController, times(1)).processPayment(orderId);
    }

    @Test
    void getPaymentStatus() {
        String orderId = "1234";
        when(paymentProcessingController.getPaymentStatus(orderId)).thenReturn("PENDING");
        PaymentStatusDTO paymentStatus = (PaymentStatusDTO) paymentProcessingService.getPaymentStatus(orderId).getBody();
        assertEquals(paymentStatus.orderId(), orderId);
        assertEquals(paymentStatus.orderStatus(), "PENDING");
    }
}

