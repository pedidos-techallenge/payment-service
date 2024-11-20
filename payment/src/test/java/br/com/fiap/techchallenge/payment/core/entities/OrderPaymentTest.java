package br.com.fiap.techchallenge.payment.core.entities;

import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.PaymentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderPaymentTest {

    // setOrderStatus
    @Test
    public void testSettingOrderStatusFromCreatedToPending() {
        OrderPayment orderPayment = new OrderPayment("1234");
        orderPayment.setOrderStatus(PaymentStatus.PENDING);

        assertEquals(PaymentStatus.PENDING, orderPayment.getOrderStatus());
    }

    @Test
    public void testSettingOrderStatusFromPendingToPaid() {
        OrderPayment orderPayment = new OrderPayment("1234", PaymentStatus.PENDING, null);
        orderPayment.setOrderStatus(PaymentStatus.PAID);

        assertEquals(PaymentStatus.PAID, orderPayment.getOrderStatus());
    }

    @Test
    public void testSettingOrderStatusFromPendingToDenied() {
        OrderPayment orderPayment = new OrderPayment("1234", PaymentStatus.PENDING, null);
        orderPayment.setOrderStatus(PaymentStatus.DENIED);

        assertEquals(PaymentStatus.DENIED, orderPayment.getOrderStatus());
    }

    @Test
    public void testSettingOrderStatusFromCreatedToDenied() {
        OrderPayment orderPayment = new OrderPayment("1234");
        try {
            orderPayment.setOrderStatus(PaymentStatus.DENIED);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid status transition, current status: CREATED, new status: DENIED", e.getMessage());
        }
    }

    @Test
    public void testSettingOrderStatusFromPaidToDenied() {
        OrderPayment orderPayment = new OrderPayment("1234", PaymentStatus.PAID, null);
        try {
            orderPayment.setOrderStatus(PaymentStatus.DENIED);
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
        assertEquals(PaymentStatus.PENDING, orderPayment.getOrderStatus());
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
