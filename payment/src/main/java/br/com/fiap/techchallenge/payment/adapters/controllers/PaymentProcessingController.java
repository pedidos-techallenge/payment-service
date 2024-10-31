package br.com.fiap.techchallenge.payment.adapters.controllers;

import br.com.fiap.techchallenge.payment.core.usecase.in.IPaymentProcessingUseCase;

public class PaymentProcessingController {
    IPaymentProcessingUseCase paymentProcessingUseCase;

    public PaymentProcessingController(IPaymentProcessingUseCase paymentProcessingUseCase) {
        this.paymentProcessingUseCase = paymentProcessingUseCase;
    }

    public String processPayment(String orderId) {
        return paymentProcessingUseCase.processPayment(orderId);
    }

    public String getPaymentStatus(String orderId) {
        return this.paymentProcessingUseCase.getPaymentStatus(orderId);
    }
}
