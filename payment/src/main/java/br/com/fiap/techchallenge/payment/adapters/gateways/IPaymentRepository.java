package br.com.fiap.techchallenge.payment.adapters.gateways;

public interface IPaymentRepository {
    String getPaymentStatus(String orderId);
}
