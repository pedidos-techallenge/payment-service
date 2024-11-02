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
    public String getPaymentStatus(String orderId) {
        return paymentRepository.getPaymentStatus(orderId);
    }

    @Override
    public void createPayment(String orderId) {
        paymentRepository.createPayment(orderId);
    }

    @Override
    public void approvePayment(String orderId, String orderStatus) {
        if(getPaymentStatus(orderId).equals("PENDING")) {
            paymentRepository.setPaymentStatus(orderId, orderStatus);
        }

    }

    @Override
    public String getQRCode(String orderId) {
        String qrCode = paymentRepository.getPaymentQRCode(orderId);
        if (qrCode == null) {
            qrCode = paymentGateway.processQRCodePayment(orderId);
            paymentRepository.setPaymentStatus(orderId, "PENDING", qrCode);
        }
        return qrCode;
    }
}
