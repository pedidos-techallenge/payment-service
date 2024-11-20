package br.com.fiap.techchallenge.payment.infrastructure.messaging;

import br.com.fiap.techchallenge.payment.adapters.gateways.IMessagePublisher;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class LocalMessagePublisher implements IMessagePublisher {
    @Override
    public void sendMessage(OrderPayment orderPayment) {
        System.out.println("Sending message to SQS");
    }
}
