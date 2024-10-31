package br.com.fiap.techchallenge.payment.infrastructure.bd;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class LocalRepository implements IPaymentRepository {
    @Override
    public String getPaymentStatus(String orderId) {
        return switch (orderId) {
            case "1234" -> "APPROVED";
            case "5678" -> "REJECTED";
            case "4321" -> "PENDING";
            default -> null;
        };
    }
}
