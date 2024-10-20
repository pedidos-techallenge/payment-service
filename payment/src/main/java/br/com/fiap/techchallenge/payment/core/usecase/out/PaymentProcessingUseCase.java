package br.com.fiap.techchallenge.payment.core.usecase.out;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import br.com.fiap.techchallenge.payment.core.usecase.in.IPaymentProcessingUseCase;

public class PaymentProcessingUseCase implements IPaymentProcessingUseCase {
    IPaymentGateway paymentGateway;

    public PaymentProcessingUseCase(IPaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public String processPayment(String orderId) {
        return this.paymentGateway.processQRCodePayment(orderId);
    }
}
