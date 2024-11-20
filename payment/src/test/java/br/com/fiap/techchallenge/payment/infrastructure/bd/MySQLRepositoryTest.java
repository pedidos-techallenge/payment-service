package br.com.fiap.techchallenge.payment.infrastructure.bd;

import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.StatusPayment;
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
        String idOrder = "1234";
        OrderPayment testOrderPayment = new OrderPayment(idOrder, StatusPayment.CREATED, null);
        mySQLRepository.createPayment(testOrderPayment);

        when(namedParameterJdbcTemplate
                .query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class))
        ).thenReturn(List.of(testOrderPayment));

        assertEquals(StatusPayment.CREATED, mySQLRepository.getPayment(idOrder).getStatusPayment());

    }

    @Test
    void setStatusPaymentUpdatesStatus() {
        String idOrder = "1234";

        OrderPayment testOrderPayment = new OrderPayment(idOrder, StatusPayment.PENDING, null);
        mySQLRepository.createPayment(testOrderPayment);
        OrderPayment testUpdatedOrderPayment = new OrderPayment(idOrder, StatusPayment.PAID, null);
        mySQLRepository.updatePayment(testUpdatedOrderPayment);

        when(namedParameterJdbcTemplate
                .query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class))
        ).thenReturn(List.of(testUpdatedOrderPayment));

        assertEquals(testUpdatedOrderPayment.getStatusPayment(), mySQLRepository.getPayment(testOrderPayment.getIdOrder()).getStatusPayment());
    }

    @Test
    void setStatusPaymentWhenPaymentIsNotFound() {
        OrderPayment testOrderPayment = new OrderPayment("1234", StatusPayment.PENDING, null);
        mySQLRepository.updatePayment(testOrderPayment);
        assertNull(mySQLRepository.getPayment(testOrderPayment.getIdOrder()));
    }
}
