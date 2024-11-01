package br.com.fiap.techchallenge.payment.adapters.gateways;

public interface IPaymentRepository {
    void createPayment(String orderId);

    void setPaymentStatus(String orderId, String status);

    void setPaymentStatus(String orderId, String status, String qrCode);

    String getPaymentStatus(String orderId);

    String getPaymentQRCode(String orderId);
}
