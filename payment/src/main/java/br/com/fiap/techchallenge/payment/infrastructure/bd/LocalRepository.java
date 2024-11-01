package br.com.fiap.techchallenge.payment.infrastructure.bd;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class LocalRepository implements IPaymentRepository {

    private ArrayList<Payment> payments = new ArrayList<>();

    private record Payment(String orderId, String status, String qrCode) {}

    @Override
    public void createPayment(String orderId){
        payments.add(new Payment(orderId, "CREATED", null));
    }

    @Override
    public void setPaymentStatus(String orderId, String status) {
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).orderId.equals(orderId)) {
                payments.set(i, new Payment(orderId, status, payments.get(i).qrCode));
            }
        }
    }

    @Override
    public void setPaymentStatus(String orderId, String status, String qrCode) {
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).orderId.equals(orderId)) {
                payments.set(i, new Payment(orderId, status, qrCode));
            }
        }
    }

    @Override
    public String getPaymentStatus(String orderId) {
        for (Payment payment : payments) {
            if (payment.orderId.equals(orderId)) {
                return payment.status;
            }
        }
        return null;
    }

    @Override
    public String getPaymentQRCode(String orderId) {
        for (Payment payment : payments) {
            if (payment.orderId.equals(orderId)) {
                return payment.qrCode;
            }
        }
        return null;
    }
}
