package br.com.fiap.techchallenge.payment.core.usecase.in;

public interface IPaymentProcessingUseCase {
    String processPayment(String orderId);

    String getPaymentStatus(String orderId);

    void createPayment(String orderId);


    void approvePayment(String orderId, String orderStatus);

    String getQRCode(String orderId);
}
