package br.com.fiap.techchallenge.payment.infrastructure.gateway;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class LocalPaymentGateway implements IPaymentGateway {
    @Override
    public String processQRCodePayment(String idOrder) {
        return "QR_CODE=12345_67890";
    }
}
