package br.com.fiap.techchallenge.payment.infrastructure.bd;

import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.StatusPayment;
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
        OrderPayment testOrderPayment = new OrderPayment("1234", StatusPayment.CREATED, null);
        mockRepository.createPayment(testOrderPayment);
        assertEquals(StatusPayment.CREATED, mockRepository.getPayment("1234").getStatusPayment());
    }

    // setStatusPayment(idOrder, status)
    @Test
    void setStatusPaymentUpdatesStatus() {
        OrderPayment testOrderPayment = new OrderPayment("1234", StatusPayment.PENDING, null);
        mockRepository.createPayment(testOrderPayment);
        OrderPayment testUpdatedOrderPayment = new OrderPayment("1234", StatusPayment.PAID, null);
        mockRepository.updatePayment(testUpdatedOrderPayment);
        assertEquals(testUpdatedOrderPayment.getStatusPayment(), mockRepository.getPayment(testOrderPayment.getIdOrder()).getStatusPayment());
    }

    @Test
    void setStatusPaymentWhenPaymentIsNotFound() {
        OrderPayment testOrderPayment = new OrderPayment("1234", StatusPayment.PENDING, null);
        mockRepository.updatePayment(testOrderPayment);
        assertNull(mockRepository.getPayment(testOrderPayment.getIdOrder()));
    }
}
