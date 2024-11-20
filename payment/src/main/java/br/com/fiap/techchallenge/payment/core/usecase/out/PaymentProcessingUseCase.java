package br.com.fiap.techchallenge.payment.core.usecase.out;

import br.com.fiap.techchallenge.payment.adapters.gateways.IMessagePublisher;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.StatusPayment;
import br.com.fiap.techchallenge.payment.core.usecase.in.IPaymentProcessingUseCase;

public class PaymentProcessingUseCase implements IPaymentProcessingUseCase {
    final IPaymentGateway paymentGateway;
    final IPaymentRepository paymentRepository;
    final IMessagePublisher messagePublisher;

    public PaymentProcessingUseCase(IPaymentGateway paymentGateway
            , IPaymentRepository paymentRepository, IMessagePublisher messagePublisher) {
        this.paymentGateway = paymentGateway;
        this.paymentRepository = paymentRepository;
        this.messagePublisher = messagePublisher;
    }

    @Override
    public StatusPayment getStatusPayment(String idOrder) {
        if (idOrder.isEmpty()) {
            throw new RuntimeException("Empty idOrder provided");
        }
        OrderPayment orderPayment = paymentRepository.getPayment(idOrder);
        return orderPayment.getStatusPayment();
    }

    @Override
    public void createPayment(String idOrder) {
        if (idOrder.isEmpty()) {
            throw new RuntimeException("Empty idOrder provided");
        }
        if (paymentRepository.getPayment(idOrder) != null) {
            throw new RuntimeException("Order already has a payment");
        }
        OrderPayment orderPayment = new OrderPayment(idOrder);
        paymentRepository.createPayment(orderPayment);
    }

    @Override
    public void approvePayment(String idOrder, StatusPayment statusPayment) {
        if (idOrder.isEmpty()) {
            throw new RuntimeException("Empty idOrder provided");
        }
        OrderPayment orderPayment = paymentRepository.getPayment(idOrder);
        orderPayment.setStatusPayment(statusPayment);
        paymentRepository.updatePayment(orderPayment);
        messagePublisher.sendMessage(orderPayment);
    }

    @Override
    public String getQRCode(String idOrder) {
        if (idOrder.isEmpty()) {
            throw new RuntimeException("Empty idOrder provided");
        }
        OrderPayment orderPayment = paymentRepository.getPayment(idOrder);
        if (orderPayment == null) {
            throw new RuntimeException("Order not found");
        }
        String qrCode = orderPayment.getQrCode();
        if (qrCode == null) {
            qrCode = paymentGateway.processQRCodePayment(idOrder);
            orderPayment.setQrCode(qrCode);
            paymentRepository.updatePayment(orderPayment);
            return qrCode;
        }
        return qrCode;
    }
}
