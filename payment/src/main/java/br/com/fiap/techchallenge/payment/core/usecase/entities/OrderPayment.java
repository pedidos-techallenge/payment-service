package br.com.fiap.techchallenge.payment.core.usecase.entities;

public class OrderPayment {
    private final String orderId;
    private OrderStatus orderStatus;
    private String qrCode;

    public OrderPayment(String orderId) {
        this.orderId = orderId;
        this.orderStatus = OrderStatus.CREATED;
    }

    public OrderPayment(String orderId, OrderStatus orderStatus, String qrCode) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.qrCode = qrCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        if (this.orderStatus.equals(OrderStatus.CREATED) && orderStatus.equals(OrderStatus.PENDING)) {
            this.orderStatus = orderStatus;
        } else if (this.orderStatus.equals(OrderStatus.PENDING) && orderStatus.equals(OrderStatus.APPROVED)) {
            this.orderStatus = orderStatus;
        } else if (this.orderStatus.equals(OrderStatus.PENDING) && orderStatus.equals(OrderStatus.REJECTED)) {
            this.orderStatus = orderStatus;
        } else {
            throw new IllegalArgumentException("Invalid status transition, current status: " + this.orderStatus + ", new status: " + orderStatus);
        }
    }

    public void setQrCode(String qrCode) {
        if (this.qrCode != null) {
            throw new IllegalArgumentException("QRCode already set");
        }
        this.setOrderStatus(OrderStatus.PENDING);
        this.qrCode = qrCode;
    }
}
