package br.com.fiap.techchallenge.payment.bdd;

import br.com.fiap.techchallenge.payment.bdd.config.SharedScenarioState;
import br.com.fiap.techchallenge.payment.infrastructure.bd.MockRepository;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestOrderPaymentApproval {
    @LocalServerPort
    private int port;

    @Autowired
    MockRepository mockRepository;

    public SharedScenarioState sharedScenarioState;

    public TestOrderPaymentApproval(SharedScenarioState sharedScenarioState) {
        this.sharedScenarioState = sharedScenarioState;
    }

    @Before
    public void setUp() {
        RestAssured.port = port;
        this.sharedScenarioState.response = null;
        mockRepository.clear();
    }

    @Quando("o gateway de pagamento sinalizou a aprovação da fatura do pedido {string}")
    public void orderPaymentIsPaid(String orderId) {
        RestAssured.given()
                .contentType("application/json")
                .body("{\"orderId\":\"" + orderId + "\",\"orderStatus\":\"PAID\"}")
                .post("/v1/payment/approve");
    }

    @Quando("o gateway de pagamento sinalizou a rejeição da fatura do pedido {string}")
    public void orderPaymentIsDenied(String orderId) {
        RestAssured.given()
                .contentType("application/json")
                .body("{\"orderId\":\"" + orderId + "\",\"orderStatus\":\"DENIED\"}")
                .post("/v1/payment/approve");
    }

    @Então("a fatura do pedido {string} deve ser marcada com o status {string}")
    public void orderPaymentChangeStatus(String orderId, String expectedOrderStatus) {
        String actualOrderStatus = RestAssured.given()
                .get("/v1/payment/status/" + orderId)
                .then()
                .extract()
                .path("orderStatus");
        assertEquals(expectedOrderStatus, actualOrderStatus);
    }

}
