package br.com.fiap.techchallenge.payment.core.entities;

import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.StatusPayment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderPaymentTest {

    // setStatusPayment
    @Test
    public void testSettingStatusPaymentFromCreatedToPending() {
        OrderPayment orderPayment = new OrderPayment("1234");
        orderPayment.setStatusPayment(StatusPayment.PENDING);

        assertEquals(StatusPayment.PENDING, orderPayment.getStatusPayment());
    }

    @Test
    public void testSettingStatusPaymentFromPendingToPaid() {
        OrderPayment orderPayment = new OrderPayment("1234", StatusPayment.PENDING, null);
        orderPayment.setStatusPayment(StatusPayment.PAID);

        assertEquals(StatusPayment.PAID, orderPayment.getStatusPayment());
    }

    @Test
    public void testSettingStatusPaymentFromPendingToDenied() {
        OrderPayment orderPayment = new OrderPayment("1234", StatusPayment.PENDING, null);
        orderPayment.setStatusPayment(StatusPayment.DENIED);

        assertEquals(StatusPayment.DENIED, orderPayment.getStatusPayment());
    }

    @Test
    public void testSettingStatusPaymentFromCreatedToDenied() {
        OrderPayment orderPayment = new OrderPayment("1234");
        try {
            orderPayment.setStatusPayment(StatusPayment.DENIED);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid status transition, current status: CREATED, new status: DENIED", e.getMessage());
        }
    }

    @Test
    public void testSettingStatusPaymentFromPaidToDenied() {
        OrderPayment orderPayment = new OrderPayment("1234", StatusPayment.PAID, null);
        try {
            orderPayment.setStatusPayment(StatusPayment.DENIED);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid status transition, current status: PAID, new status: DENIED", e.getMessage());
        }
    }

    // setQrCode

    @Test
    public void testSettingQrCode() {
        OrderPayment orderPayment = new OrderPayment("1234");
        orderPayment.setQrCode("QR_CODE_1234567");

        assertEquals("QR_CODE_1234567", orderPayment.getQrCode());
        assertEquals(StatusPayment.PENDING, orderPayment.getStatusPayment());
    }

    @Test
    public void testSettingQrCodeTwice() {
        OrderPayment orderPayment = new OrderPayment("1234");
        orderPayment.setQrCode("QR_CODE_1234567");
        try {
            orderPayment.setQrCode("QR_CODE_98765432");
        } catch (IllegalArgumentException e) {
            assertEquals("QRCode already set", e.getMessage());
        }
    }
}
