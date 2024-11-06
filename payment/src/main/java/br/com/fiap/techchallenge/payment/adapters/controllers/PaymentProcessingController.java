package br.com.fiap.techchallenge.payment.adapters.controllers;

import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderStatus;
import br.com.fiap.techchallenge.payment.core.usecase.in.IPaymentProcessingUseCase;

public class PaymentProcessingController {
    IPaymentProcessingUseCase paymentProcessingUseCase;

    public PaymentProcessingController(IPaymentProcessingUseCase paymentProcessingUseCase) {
        this.paymentProcessingUseCase = paymentProcessingUseCase;
    }

    public OrderStatus getPaymentStatus(String orderId) {
        return this.paymentProcessingUseCase.getPaymentStatus(orderId);
    }

    public void createPayment(String orderId) {
        paymentProcessingUseCase.createPayment(orderId);
    }

    public void approvePayment(String orderId, String orderStatus) {
        paymentProcessingUseCase.approvePayment(orderId, OrderStatus.valueOf(orderStatus));
    }

    public String getQRCode(String orderId) {
        return paymentProcessingUseCase.getQRCode(orderId);
    }
}
