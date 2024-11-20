package br.com.fiap.techchallenge.payment.infrastructure.bd;

import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MockRepositoryTest {

    private MockRepository mockRepository;

    @BeforeEach
    void setUp() {
        mockRepository = new MockRepository();
    }

    // createPayment
    @Test
    void testCreatePaymentInsertsNewPayment() {
        OrderPayment testOrderPayment = new OrderPayment("1234", PaymentStatus.CREATED, null);
        mockRepository.createPayment(testOrderPayment);
        assertEquals(PaymentStatus.CREATED, mockRepository.getPayment("1234").getOrderStatus());
    }

    // setPaymentStatus(orderId, status)
    @Test
    void setPaymentStatusUpdatesStatus() {
        OrderPayment testOrderPayment = new OrderPayment("1234", PaymentStatus.PENDING, null);
        mockRepository.createPayment(testOrderPayment);
        OrderPayment testUpdatedOrderPayment = new OrderPayment("1234", PaymentStatus.PAID, null);
        mockRepository.updatePayment(testUpdatedOrderPayment);
        assertEquals(testUpdatedOrderPayment.getOrderStatus(), mockRepository.getPayment(testOrderPayment.getOrderId()).getOrderStatus());
    }

    @Test
    void setPaymentStatusWhenPaymentIsNotFound() {
        OrderPayment testOrderPayment = new OrderPayment("1234", PaymentStatus.PENDING, null);
        mockRepository.updatePayment(testOrderPayment);
        assertNull(mockRepository.getPayment(testOrderPayment.getOrderId()));
    }
}
