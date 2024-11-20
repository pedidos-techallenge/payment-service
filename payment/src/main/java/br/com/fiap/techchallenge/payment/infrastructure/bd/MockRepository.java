package br.com.fiap.techchallenge.payment.infrastructure.bd;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.StatusPayment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
@Profile("test")
public class MockRepository implements IPaymentRepository {

    private ArrayList<Payment> payments = new ArrayList<>();

    private record Payment(String idOrder, String status, String qrCode) {}

    @Override
    public void createPayment(OrderPayment orderPayment){
        payments.add(new Payment(orderPayment.getIdOrder(), orderPayment.getStatusPayment().name(), orderPayment.getQrCode()));
    }

    @Override
    public void updatePayment(OrderPayment orderPayment) {
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).idOrder.equals(orderPayment.getIdOrder())) {
                payments.set(i, new Payment(orderPayment.getIdOrder(), orderPayment.getStatusPayment().name(), orderPayment.getQrCode()));
            }
        }
    }

    @Override
    public OrderPayment getPayment(String idOrder) {
        for (Payment payment : payments) {
            if (payment.idOrder.equals(idOrder)) {
                return new OrderPayment(idOrder, StatusPayment.valueOf(payment.status), payment.qrCode);
            }
        }
        return null;
    }

    public void clear() {
        this.payments = new ArrayList<>();
    }
}
