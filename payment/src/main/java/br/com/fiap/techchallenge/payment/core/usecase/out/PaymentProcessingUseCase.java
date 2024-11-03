package br.com.fiap.techchallenge.payment.core.usecase.out;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderStatus;
import br.com.fiap.techchallenge.payment.core.usecase.in.IPaymentProcessingUseCase;

public class PaymentProcessingUseCase implements IPaymentProcessingUseCase {
    IPaymentGateway paymentGateway;
    IPaymentRepository paymentRepository;

    public PaymentProcessingUseCase(IPaymentGateway paymentGateway, IPaymentRepository paymentRepository) {
        this.paymentGateway = paymentGateway;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public OrderStatus getPaymentStatus(String orderId) {
        OrderPayment orderPayment = paymentRepository.getPayment(orderId);
        return orderPayment.getOrderStatus();
    }

    @Override
    public void createPayment(String orderId) {
        OrderPayment orderPayment = new OrderPayment(orderId);
        paymentRepository.createPayment(orderPayment);
    }

    @Override
    public void approvePayment(String orderId, OrderStatus orderStatus) {
        OrderPayment orderPayment = paymentRepository.getPayment(orderId);
        orderPayment.setOrderStatus(orderStatus);
        paymentRepository.updatePayment(orderPayment);
    }

    @Override
    public String getQRCode(String orderId) {
        OrderPayment orderPayment = paymentRepository.getPayment(orderId);
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
