package br.com.fiap.techchallenge.payment.infrastructure.config;

import br.com.fiap.techchallenge.payment.adapters.gateways.IMessagePublisher;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PaymentProcessingUseCaseConfigTest {

    @Mock
    private IPaymentGateway paymentGateway;

    @Mock
    private IPaymentRepository paymentRepository;

    @Mock
    private IMessagePublisher messagePublisher;

    @Test
    void paymentProcessingUseCaseBeanIsCreated() {
        PaymentProcessingUseCaseConfig paymentProcessingUseCaseConfig = new PaymentProcessingUseCaseConfig();

        assertDoesNotThrow(() -> {
            paymentProcessingUseCaseConfig.getPaymentProcessingUseCase(paymentGateway, paymentRepository, messagePublisher);
        });
    }
}