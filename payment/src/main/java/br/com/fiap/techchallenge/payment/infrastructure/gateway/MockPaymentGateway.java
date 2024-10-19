package br.com.fiap.techchallenge.payment.infrastructure.gateway;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockPaymentGateway implements IPaymentGateway {
}
