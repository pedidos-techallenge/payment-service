package br.com.fiap.techchallenge.payment.core.usecase.in;

public interface IPaymentProcessingUseCase {
    void processPayment(String orderId);
}
