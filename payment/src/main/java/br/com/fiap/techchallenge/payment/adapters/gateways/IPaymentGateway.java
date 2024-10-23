package br.com.fiap.techchallenge.payment.adapters.gateways;

public interface IPaymentGateway {
    String processQRCodePayment(String orderId);
}
