package br.com.fiap.techchallenge.payment.infrastructure.bd;

import br.com.fiap.techchallenge.payment.core.usecase.entities.PaymentStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import br.com.fiap.techchallenge.payment.adapters.gateways.IPaymentRepository;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Profile("!test")
public class MySQLRepository implements IPaymentRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MySQLRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    @Override
    public void createPayment(OrderPayment orderPayment) {
        String sql = "" +
                " INSERT INTO orderpayments.tb_order_payments (order_id, payment_status, qr_code)" +
                " VALUES (:order_id, :payment_status, :qr_code)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("order_id", orderPayment.getOrderId());
        parameters.addValue("payment_status", orderPayment.getOrderStatus().toString());
        parameters.addValue("qr_code", orderPayment.getQrCode());
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Transactional
    @Override
    public void updatePayment(OrderPayment orderPayment) {
        String sql = "" +
                " UPDATE orderpayments.tb_order_payments" +
                " SET payment_status = :payment_status, qr_code = :qr_code" +
                " WHERE order_id = :order_id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("order_id", orderPayment.getOrderId());
        parameters.addValue("payment_status", orderPayment.getOrderStatus().toString());
        parameters.addValue("qr_code", orderPayment.getQrCode());
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public OrderPayment getPayment(String orderId) {
        String sql = "SELECT * FROM orderpayments.tb_order_payments WHERE order_id = :order_id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("order_id", orderId);
        RowMapper<OrderPayment> rowMapper = new RowMapper<OrderPayment>() {
            @Override
            public OrderPayment mapRow(@NotNull ResultSet rs, int rowNum) throws SQLException {
                return new OrderPayment(
                    rs.getString("order_id"),
                    PaymentStatus.valueOf(rs.getString("payment_status")),
                    rs.getString("qr_code")
                );
            }
        };
        return namedParameterJdbcTemplate.query(sql, parameters, rowMapper).stream().findFirst().orElse(null);
    }
}
