package br.com.fiap.techchallenge.payment.infrastructure.config;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import br.com.fiap.techchallenge.payment.core.usecase.out.PaymentProcessingUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentProcessingUseCaseConfig {
    @Bean
    PaymentProcessingUseCase getPaymentProcessingUseCase(IPaymentGateway paymentGateway, IPaymentRepository paymentRepository) {
        return new PaymentProcessingUseCase(paymentGateway, paymentRepository);
    }
}
