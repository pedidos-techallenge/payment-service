package br.com.fiap.techchallenge.payment.infrastructure.config;


import br.com.fiap.techchallenge.payment.core.usecase.out.PaymentProcessingUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PaymentProcessingContollerConfigTest {

    @Mock
    private PaymentProcessingUseCase paymentProcessingUseCase;


    @Test
    void paymentProcessingUseCaseBeanIsCreated() {
        PaymentProcessingControllerConfig paymentProcessingContollerConfig = new PaymentProcessingControllerConfig();

        assertDoesNotThrow(() -> {
            paymentProcessingContollerConfig.getPaymentProcessingController(paymentProcessingUseCase);
        });
    }
}