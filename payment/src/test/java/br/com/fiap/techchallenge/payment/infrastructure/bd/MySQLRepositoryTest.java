package br.com.fiap.techchallenge.payment.infrastructure.bd;

import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class MySQLRepositoryTest {

    private MySQLRepository mySQLRepository;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mySQLRepository = new MySQLRepository(namedParameterJdbcTemplate);
    }

    @Test
    void testCreatePaymentInsertsNewPayment() {
        String orderId = "1234";
        OrderPayment testOrderPayment = new OrderPayment(orderId, OrderStatus.CREATED, null);
        mySQLRepository.createPayment(testOrderPayment);

        when(namedParameterJdbcTemplate
                .query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class))
        ).thenReturn(List.of(testOrderPayment));

        assertEquals(OrderStatus.CREATED, mySQLRepository.getPayment(orderId).getOrderStatus());

    }

    @Test
    void setPaymentStatusUpdatesStatus() {
        String orderId = "1234";

        OrderPayment testOrderPayment = new OrderPayment(orderId, OrderStatus.PENDING, null);
        mySQLRepository.createPayment(testOrderPayment);
        OrderPayment testUpdatedOrderPayment = new OrderPayment(orderId, OrderStatus.APPROVED, null);
        mySQLRepository.updatePayment(testUpdatedOrderPayment);

        when(namedParameterJdbcTemplate
                .query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class))
        ).thenReturn(List.of(testUpdatedOrderPayment));

        assertEquals(testUpdatedOrderPayment.getOrderStatus(), mySQLRepository.getPayment(testOrderPayment.getOrderId()).getOrderStatus());
    }

    @Test
    void setPaymentStatusWhenPaymentIsNotFound() {
        OrderPayment testOrderPayment = new OrderPayment("1234", OrderStatus.PENDING, null);
        mySQLRepository.updatePayment(testOrderPayment);
        assertNull(mySQLRepository.getPayment(testOrderPayment.getOrderId()));
    }
}
