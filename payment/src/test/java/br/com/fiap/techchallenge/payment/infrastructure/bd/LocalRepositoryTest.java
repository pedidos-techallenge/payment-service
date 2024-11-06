package br.com.fiap.techchallenge.payment.infrastructure.bd;

import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocalRepositoryTest {

    private LocalRepository localRepository;

    @BeforeEach
    void setUp() {
        localRepository = new LocalRepository();
    }

    // createPayment
    @Test
    void testCreatePaymentInsertsNewPayment() {
        OrderPayment testOrderPayment = new OrderPayment("1234", OrderStatus.CREATED, null);
        localRepository.createPayment(testOrderPayment);
        assertEquals(OrderStatus.CREATED, localRepository.getPayment("1234").getOrderStatus());
    }

    // setPaymentStatus(orderId, status)
    @Test
    void setPaymentStatusUpdatesStatus() {
        OrderPayment testOrderPayment = new OrderPayment("1234", OrderStatus.PENDING, null);
        localRepository.createPayment(testOrderPayment);
        OrderPayment testUpdatedOrderPayment = new OrderPayment("1234", OrderStatus.APPROVED, null);
        localRepository.updatePayment(testUpdatedOrderPayment);
        assertEquals(testUpdatedOrderPayment.getOrderStatus(), localRepository.getPayment(testOrderPayment.getOrderId()).getOrderStatus());
    }

    @Test
    void setPaymentStatusWhenPaymentIsNotFound() {
        OrderPayment testOrderPayment = new OrderPayment("1234", OrderStatus.PENDING, null);
        localRepository.updatePayment(testOrderPayment);
        assertNull(localRepository.getPayment(testOrderPayment.getOrderId()));
    }
}
