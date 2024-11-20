package br.com.fiap.techchallenge.payment.adapters.gateways;

import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;

public interface IMessagePublisher {
    void sendMessage(OrderPayment orderPayment);
}
