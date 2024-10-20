package br.com.fiap.techchallenge.payment.infrastructure.gateway;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentGateway;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Profile("test")
public class MockLocalPaymentGateway implements IPaymentGateway {

    static final String MOCK_QR_CODE = "99999";
    static final ArrayList<String> MOCK_ORDER_IDS = new ArrayList<String>();

    public MockLocalPaymentGateway() {
        MOCK_ORDER_IDS.add("1234");
    }

    @Override
    public String processQRCodePayment(String orderId) {
        if (!MOCK_ORDER_IDS.contains(orderId)) {
            throw new RuntimeException("Order not found");
        }
        System.out.println("Processing payment for order: " + orderId);
        return "MOCK_QR_CODE=99999";
    }
}
