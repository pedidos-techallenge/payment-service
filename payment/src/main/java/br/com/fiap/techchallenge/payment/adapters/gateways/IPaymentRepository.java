package br.com.fiap.techchallenge.payment.adapters.gateways;

import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;

public interface IPaymentRepository {
    void createPayment(OrderPayment orderPayment);

    void updatePayment(OrderPayment orderPayment);

    OrderPayment getPayment(String orderId);
}
