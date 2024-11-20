package br.com.fiap.techchallenge.payment.core.usecase.in;

import br.com.fiap.techchallenge.payment.core.usecase.entities.PaymentStatus;

public interface IPaymentProcessingUseCase {

    PaymentStatus getPaymentStatus(String orderId);

    void createPayment(String orderId);


    void approvePayment(String orderId, PaymentStatus paymentStatus);

    String getQRCode(String orderId);
}
