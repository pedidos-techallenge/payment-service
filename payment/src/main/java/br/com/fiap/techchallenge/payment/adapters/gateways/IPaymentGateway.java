package br.com.fiap.techchallenge.payment.adapters.gateways;

public interface IPaymentGateway {
    @SuppressWarnings("SameReturnValue")
    String processQRCodePayment(String idOrder);
}
