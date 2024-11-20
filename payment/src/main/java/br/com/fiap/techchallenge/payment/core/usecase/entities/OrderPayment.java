package br.com.fiap.techchallenge.payment.core.usecase.entities;

public class OrderPayment {
    private final String orderId;
    private PaymentStatus paymentStatus;
    private String qrCode;

    public OrderPayment(String orderId) {
        this.orderId = orderId;
        this.paymentStatus = PaymentStatus.CREATED;
    }

    public OrderPayment(String orderId, PaymentStatus paymentStatus, String qrCode) {
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
        this.qrCode = qrCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public PaymentStatus getOrderStatus() {
        return paymentStatus;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setOrderStatus(PaymentStatus paymentStatus) {
        if (this.paymentStatus.equals(PaymentStatus.CREATED) && paymentStatus.equals(PaymentStatus.PENDING)) {
            this.paymentStatus = paymentStatus;
        } else if (this.paymentStatus.equals(PaymentStatus.PENDING) && paymentStatus.equals(PaymentStatus.PAID)) {
            this.paymentStatus = paymentStatus;
        } else if (this.paymentStatus.equals(PaymentStatus.PENDING) && paymentStatus.equals(PaymentStatus.DENIED)) {
            this.paymentStatus = paymentStatus;
        } else {
            throw new IllegalArgumentException("Invalid status transition, current status: " + this.paymentStatus + ", new status: " + paymentStatus);
        }
    }

    public void setQrCode(String qrCode) {
        if (this.qrCode != null) {
            throw new IllegalArgumentException("QRCode already set");
        }
        this.setOrderStatus(PaymentStatus.PENDING);
        this.qrCode = qrCode;
    }
}
