package br.com.fiap.techchallenge.payment.infrastructure.gateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalPaymentGatewayTest {

    @Test
    void processQRCodePaymentReturnsvalue() {
        LocalPaymentGateway localPaymentGateway = new LocalPaymentGateway();
        String idOrder = "order123";
        String qrCode = localPaymentGateway.processQRCodePayment(idOrder);
        assertEquals("QR_CODE=12345_67890", qrCode);
    }
}
