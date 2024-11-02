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
    void testGetQRCodeHandlesEmptyOrderId() {
        String orderId = "";

        when(paymentProcessingController
                .getQRCode(orderId))
                .thenReturn(null);
        int responseStatus = paymentProcessingService.getQRCode(orderId).getStatusCode().value();

        assertEquals(404, responseStatus);
        verify(paymentProcessingController, times(1)).getQRCode(orderId);
    }

    @Test
    void testGetQRCodeHandlesInvalidOrderId() {
        String orderId = "invalid_order";

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
    void testGetPaymentStatus() {
        String orderId = "1234";
        when(paymentProcessingController.getPaymentStatus(orderId)).thenReturn("PENDING");
        PaymentStatusDTO paymentStatus = (PaymentStatusDTO) paymentProcessingService.getPaymentStatus(orderId).getBody();
        assertEquals(paymentStatus.orderId(), orderId);
        assertEquals(paymentStatus.orderStatus(), "PENDING");
    }
}

