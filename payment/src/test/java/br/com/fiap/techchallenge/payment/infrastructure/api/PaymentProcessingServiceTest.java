package br.com.fiap.techchallenge.payment.infrastructure.api;

import br.com.fiap.techchallenge.payment.adapters.controllers.PaymentProcessingController;
import br.com.fiap.techchallenge.payment.core.usecase.entities.StatusPayment;
import br.com.fiap.techchallenge.payment.infrastructure.dto.OrderRequestDTO;
import br.com.fiap.techchallenge.payment.infrastructure.dto.StatusPaymentDTO;
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
        String idOrder = "1234";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(idOrder, null);

        int responseStatus = paymentProcessingService.createPayment(orderRequestDTO).getStatusCode().value();

        assertEquals(200, responseStatus);
        verify(paymentProcessingController, times(1)).createPayment(idOrder);
    }

    @Test
    void testCreatePaymentReturns500WhenUnsucessfull() {
        String idOrder = "1234";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(idOrder, null);

        doThrow(new RuntimeException()).when(paymentProcessingController).createPayment(idOrder);
        int responseStatus = paymentProcessingService.createPayment(orderRequestDTO).getStatusCode().value();

        assertEquals(500, responseStatus);
        verify(paymentProcessingController, times(1)).createPayment(idOrder);
    }

    @Test
    void testApprovePaymentReturnsOKWhenSucessfull() {
        String idOrder = "1234";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(idOrder, StatusPayment.PAID.name());

        int responseStatus = paymentProcessingService.approvePayment(orderRequestDTO).getStatusCode().value();

        assertEquals(200, responseStatus);
        verify(paymentProcessingController, times(1)).approvePayment(idOrder, StatusPayment.PAID.name());
    }

    @Test
    void testApprovePaymentReturns500WhenUnsucessfull() {
        String idOrder = "1234";
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(idOrder, StatusPayment.PAID.name());

        doThrow(new RuntimeException()).when(paymentProcessingController).approvePayment(idOrder, StatusPayment.PAID.name());
        int responseStatus = paymentProcessingService.approvePayment(orderRequestDTO).getStatusCode().value();

        assertEquals(500, responseStatus);
        verify(paymentProcessingController, times(1)).approvePayment(idOrder, StatusPayment.PAID.name());
    }

    @Test
    void testGetQRCodeReturnsCodeSucessfully() {
        String idOrder = "1234";
        String expectedQRCode = "QR_CODE_12345";
        QRCodeResponseDTO qrCodeResponseDTO = new QRCodeResponseDTO(idOrder, expectedQRCode);

        when(paymentProcessingController.getQRCode(idOrder)).thenReturn(expectedQRCode);
        QRCodeResponseDTO actualQRCodeResponse = (QRCodeResponseDTO) paymentProcessingService.getQRCode(idOrder).getBody();

        assertEquals(qrCodeResponseDTO.qrCode(), actualQRCodeResponse.qrCode());
        assertEquals(qrCodeResponseDTO.idOrder(), actualQRCodeResponse.idOrder());
        verify(paymentProcessingController, times(1)).getQRCode(idOrder);
    }

    @Test
    void testGetQRCodeHandlesNullidOrder() {
        String idOrder = null;

        when(paymentProcessingController
                .getQRCode(idOrder))
                .thenReturn(null);
        int responseStatus = paymentProcessingService.getQRCode(idOrder).getStatusCode().value();

        assertEquals(404, responseStatus);
        verify(paymentProcessingController, times(1)).getQRCode(idOrder);
    }

    @Test
    void testGetQRCodeHandlesInvalidIdOrder() {
        String idOrder = "";

        when(paymentProcessingController
                .getQRCode(idOrder))
                .thenReturn(null);
        int responseStatus = paymentProcessingService.getQRCode(idOrder).getStatusCode().value();

        assertEquals(404, responseStatus);
        verify(paymentProcessingController, times(1)).getQRCode(idOrder);
    }

    @Test
    void testGetQRCodeHandlesInternalServerError() {
        String idOrder = "1234";

        when(paymentProcessingController.getQRCode(idOrder)).thenThrow(new RuntimeException());
        int responseStatus = paymentProcessingService.getQRCode(idOrder).getStatusCode().value();

        assertEquals(500, responseStatus);
        verify(paymentProcessingController, times(1)).getQRCode(idOrder);
    }

    @Test
    void testGetStatusReturnsStatusPaymentSuccessfully() {
        String idOrder = "1234";
        when(paymentProcessingController.getStatusPayment(idOrder)).thenReturn(StatusPayment.PENDING);
        StatusPaymentDTO statusPayment = (StatusPaymentDTO) paymentProcessingService.getStatusPayment(idOrder).getBody();
        assertEquals(statusPayment.idOrder(), idOrder);
        assertEquals(statusPayment.statusPayment(), StatusPayment.PENDING.name());
    }

    @Test
    void testGetStatusPaymentHandlesNullIdOrder() {
        String idOrder = null;

        when(paymentProcessingController
                .getStatusPayment(idOrder))
                .thenReturn(null);
        int responseStatus = paymentProcessingService.getStatusPayment(idOrder).getStatusCode().value();

        assertEquals(404, responseStatus);
        verify(paymentProcessingController, times(1)).getStatusPayment(idOrder);
    }

    @Test
    void testGetStatusPaymentHandlesInvalidIdOrder() {
        String idOrder = "";

        when(paymentProcessingController
                .getStatusPayment(idOrder))
                .thenReturn(null);
        int responseStatus = paymentProcessingService.getStatusPayment(idOrder).getStatusCode().value();

        assertEquals(404, responseStatus);
        verify(paymentProcessingController, times(1)).getStatusPayment(idOrder);
    }

    @Test
    void testGetStatusPaymentHandlesInternalServerError() {
        String idOrder = "1234";

        when(paymentProcessingController.getStatusPayment(idOrder)).thenThrow(new RuntimeException());
        int responseStatus = paymentProcessingService.getStatusPayment(idOrder).getStatusCode().value();

        assertEquals(500, responseStatus);
        verify(paymentProcessingController, times(1)).getStatusPayment(idOrder);
    }
}

