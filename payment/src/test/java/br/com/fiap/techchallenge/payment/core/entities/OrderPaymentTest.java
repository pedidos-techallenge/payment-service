package br.com.fiap.techchallenge.payment.core.entities;

import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderPaymentTest {

    // setOrderStatus
    @Test
    public void testSettingOrderStatusFromCreatedToPending() {
        OrderPayment orderPayment = new OrderPayment("1234");
        orderPayment.setOrderStatus(OrderStatus.PENDING);

        assertEquals(OrderStatus.PENDING, orderPayment.getOrderStatus());
    }

    @Test
    public void testSettingOrderStatusFromPendingToApproved() {
        OrderPayment orderPayment = new OrderPayment("1234", OrderStatus.PENDING, null);
        orderPayment.setOrderStatus(OrderStatus.APPROVED);

        assertEquals(OrderStatus.APPROVED, orderPayment.getOrderStatus());
    }

    @Test
    public void testSettingOrderStatusFromPendingToRejected() {
        OrderPayment orderPayment = new OrderPayment("1234", OrderStatus.PENDING, null);
        orderPayment.setOrderStatus(OrderStatus.REJECTED);

        assertEquals(OrderStatus.REJECTED, orderPayment.getOrderStatus());
    }

    @Test
    public void testSettingOrderStatusFromCreatedToRejected() {
        OrderPayment orderPayment = new OrderPayment("1234");
        try {
            orderPayment.setOrderStatus(OrderStatus.REJECTED);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid status transition, current status: CREATED, new status: REJECTED", e.getMessage());
        }
    }

    @Test
    public void testSettingOrderStatusFromApprovedToRejected() {
        OrderPayment orderPayment = new OrderPayment("1234", OrderStatus.APPROVED, null);
        try {
            orderPayment.setOrderStatus(OrderStatus.REJECTED);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid status transition, current status: APPROVED, new status: REJECTED", e.getMessage());
        }
    }

    // setQrCode

    @Test
    public void testSettingQrCode() {
        OrderPayment orderPayment = new OrderPayment("1234");
        orderPayment.setQrCode("QR_CODE_1234567");

        assertEquals("QR_CODE_1234567", orderPayment.getQrCode());
        assertEquals(OrderStatus.PENDING, orderPayment.getOrderStatus());
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
