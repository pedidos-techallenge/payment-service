package br.com.fiap.techchallenge.payment.core.usecase.out;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import br.com.fiap.techchallenge.payment.core.usecase.in.IPaymentProcessingUseCase;

public class PaymentProcessingUseCase implements IPaymentProcessingUseCase {
    IPaymentGateway paymentGateway;
    IPaymentRepository paymentRepository;

    public PaymentProcessingUseCase(IPaymentGateway paymentGateway, IPaymentRepository paymentRepository) {
        this.paymentGateway = paymentGateway;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public String processPayment(String orderId) {
        return this.paymentGateway.processQRCodePayment(orderId);
    }

    @Override
    public String getPaymentStatus(String orderId) {
        return paymentRepository.getPaymentStatus(orderId);
    }
}
