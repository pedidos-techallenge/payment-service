package br.com.fiap.techchallenge.payment.infrastructure.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthStatusTest {

    @Test
    void healthEndpointReturnsOk() {
        HealthStatus healthStatus = new HealthStatus();
        assertEquals(healthStatus.health(), "OK");
    }
}
