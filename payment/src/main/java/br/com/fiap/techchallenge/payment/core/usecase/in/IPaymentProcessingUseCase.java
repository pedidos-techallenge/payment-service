package br.com.fiap.techchallenge.payment.core.usecase.in;

public interface IPaymentProcessingUseCase {
    String processPayment(String orderId);
}
