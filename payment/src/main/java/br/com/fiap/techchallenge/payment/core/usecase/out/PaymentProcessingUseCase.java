package br.com.fiap.techchallenge.payment.core.usecase.out;

import br.com.fiap.techchallenge.payment.adapters.gateways.IMessagePublisher;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.PaymentStatus;
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
    public PaymentStatus getPaymentStatus(String orderId) {
        if (orderId.isEmpty()) {
            throw new RuntimeException("Empty orderId provided");
        }
        OrderPayment orderPayment = paymentRepository.getPayment(orderId);
        return orderPayment.getOrderStatus();
    }

    @Override
    public void createPayment(String orderId) {
        if (orderId.isEmpty()) {
            throw new RuntimeException("Empty orderId provided");
        }
        if (paymentRepository.getPayment(orderId) != null) {
            throw new RuntimeException("Order already has a payment");
        }
        OrderPayment orderPayment = new OrderPayment(orderId);
        paymentRepository.createPayment(orderPayment);
    }

    @Override
    public void approvePayment(String orderId, PaymentStatus paymentStatus) {
        if (orderId.isEmpty()) {
            throw new RuntimeException("Empty orderId provided");
        }
        OrderPayment orderPayment = paymentRepository.getPayment(orderId);
        orderPayment.setOrderStatus(paymentStatus);
        paymentRepository.updatePayment(orderPayment);
        messagePublisher.sendMessage(orderPayment);
    }

    @Override
    public String getQRCode(String orderId) {
        if (orderId.isEmpty()) {
            throw new RuntimeException("Empty orderId provided");
        }
        OrderPayment orderPayment = paymentRepository.getPayment(orderId);
        if (orderPayment == null) {
            throw new RuntimeException("Order not found");
        }
        String qrCode = orderPayment.getQrCode();
        if (qrCode == null) {
            qrCode = paymentGateway.processQRCodePayment(orderId);
            orderPayment.setQrCode(qrCode);
            paymentRepository.updatePayment(orderPayment);
            return qrCode;
        }
        return qrCode;
    }
}
