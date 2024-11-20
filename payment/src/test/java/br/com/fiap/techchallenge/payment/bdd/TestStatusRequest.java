package br.com.fiap.techchallenge.payment.bdd;

import br.com.fiap.techchallenge.payment.bdd.config.SharedScenarioState;
import br.com.fiap.techchallenge.payment.infrastructure.bd.MockRepository;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStatusRequest {
    @LocalServerPort
    private int port;

    @Autowired
    MockRepository mockRepository;

    public SharedScenarioState sharedScenarioState;

    public TestStatusRequest(SharedScenarioState sharedScenarioState) {
        this.sharedScenarioState = sharedScenarioState;
    }

    @Before
    public void setUp() {
        RestAssured.port = port;
        this.sharedScenarioState.response = null;
        mockRepository.clear();
    }

    @Dado("houve a verificação de status da fatura {string}")
    public void orderStatusPaymentRequest(String idOrder) {
        this.sharedScenarioState.response = RestAssured.get("/v1/payment/status/" + idOrder);
    }

    @Então("Deve ser retornado uma resposta com o status {string}")
    public void checkResponse(String expectedStatus) {
        String actualStatus = this.sharedScenarioState.response.then()
                .statusCode(200)
                .extract()
                .path("statusPayment");
        assertEquals(expectedStatus, actualStatus);
    }
}
