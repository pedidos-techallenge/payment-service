package br.com.fiap.techchallenge.payment.core.usecase.entities;

public class OrderPayment {
    private final String idOrder;
    private StatusPayment statusPayment;
    private String qrCode;

    public OrderPayment(String idOrder) {
        this.idOrder = idOrder;
        this.statusPayment = StatusPayment.CREATED;
    }

    public OrderPayment(String idOrder, StatusPayment statusPayment, String qrCode) {
        this.idOrder = idOrder;
        this.statusPayment = statusPayment;
        this.qrCode = qrCode;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public StatusPayment getStatusPayment() {
        return statusPayment;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setStatusPayment(StatusPayment statusPayment) {
        if (this.statusPayment.equals(StatusPayment.CREATED) && statusPayment.equals(StatusPayment.PENDING)) {
            this.statusPayment = statusPayment;
        } else if (this.statusPayment.equals(StatusPayment.PENDING) && statusPayment.equals(StatusPayment.PAID)) {
            this.statusPayment = statusPayment;
        } else if (this.statusPayment.equals(StatusPayment.PENDING) && statusPayment.equals(StatusPayment.DENIED)) {
            this.statusPayment = statusPayment;
        } else {
            throw new IllegalArgumentException("Invalid status transition, current status: " + this.statusPayment + ", new status: " + statusPayment);
        }
    }

    public void setQrCode(String qrCode) {
        if (this.qrCode != null) {
            throw new IllegalArgumentException("QRCode already set");
        }
        this.setStatusPayment(StatusPayment.PENDING);
        this.qrCode = qrCode;
    }
}
