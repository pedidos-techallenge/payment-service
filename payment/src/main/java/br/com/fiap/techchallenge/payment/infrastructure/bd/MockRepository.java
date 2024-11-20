package br.com.fiap.techchallenge.payment.infrastructure.bd;

import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.PaymentStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
@Profile("test")
public class MockRepository implements IPaymentRepository {

    private ArrayList<Payment> payments = new ArrayList<>();

    private record Payment(String orderId, String status, String qrCode) {}

    @Override
    public void createPayment(OrderPayment orderPayment){
        payments.add(new Payment(orderPayment.getOrderId(), orderPayment.getOrderStatus().name(), orderPayment.getQrCode()));
    }

    @Override
    public void updatePayment(OrderPayment orderPayment) {
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).orderId.equals(orderPayment.getOrderId())) {
                payments.set(i, new Payment(orderPayment.getOrderId(), orderPayment.getOrderStatus().name(), orderPayment.getQrCode()));
            }
        }
    }

    @Override
    public OrderPayment getPayment(String orderId) {
        for (Payment payment : payments) {
            if (payment.orderId.equals(orderId)) {
                return new OrderPayment(orderId, PaymentStatus.valueOf(payment.status), payment.qrCode);
            }
        }
        return null;
    }

    public void clear() {
        this.payments = new ArrayList<>();
    }
}
