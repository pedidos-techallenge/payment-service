package br.com.fiap.techchallenge.payment.infrastructure.gateway;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalPaymentGateway implements IPaymentGateway {
    @Override
    public String processQRCodePayment(String orderId) {
        System.out.println("Processing payment for order: " + orderId);
        return "QR_CODE=12345_67890";
    }
}
