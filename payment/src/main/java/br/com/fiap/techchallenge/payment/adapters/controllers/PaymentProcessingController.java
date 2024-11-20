package br.com.fiap.techchallenge.payment.adapters.controllers;

import br.com.fiap.techchallenge.payment.core.usecase.entities.StatusPayment;
import br.com.fiap.techchallenge.payment.core.usecase.in.IPaymentProcessingUseCase;

public class PaymentProcessingController {
    final IPaymentProcessingUseCase paymentProcessingUseCase;

    public PaymentProcessingController(IPaymentProcessingUseCase paymentProcessingUseCase) {
        this.paymentProcessingUseCase = paymentProcessingUseCase;
    }

    public StatusPayment getStatusPayment(String idOrder) {
        return this.paymentProcessingUseCase.getStatusPayment(idOrder);
    }

    public void createPayment(String idOrder) {
        paymentProcessingUseCase.createPayment(idOrder);
    }

    public void approvePayment(String idOrder, String statusPayment) {
        paymentProcessingUseCase.approvePayment(idOrder, StatusPayment.valueOf(statusPayment));
    }

    public String getQRCode(String idOrder) {
        return paymentProcessingUseCase.getQRCode(idOrder);
    }
}
