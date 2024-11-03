package br.com.fiap.techchallenge.payment.core.usecase.in;

import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderStatus;

public interface IPaymentProcessingUseCase {

    OrderStatus getPaymentStatus(String orderId);

    void createPayment(String orderId);


    void approvePayment(String orderId, OrderStatus orderStatus);

    String getQRCode(String orderId);
}
