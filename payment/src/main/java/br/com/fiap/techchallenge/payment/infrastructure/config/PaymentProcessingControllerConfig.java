package br.com.fiap.techchallenge.payment.infrastructure.config;

import br.com.fiap.techchallenge.payment.adapters.controllers.PaymentProcessingController;
import br.com.fiap.techchallenge.payment.core.usecase.out.PaymentProcessingUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentProcessingControllerConfig {
    @Bean
    PaymentProcessingController getPaymentProcessingController(PaymentProcessingUseCase paymentProcessingUseCase) {
        return new PaymentProcessingController(paymentProcessingUseCase);
    }
}
