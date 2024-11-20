package br.com.fiap.techchallenge.payment.bdd;


import br.com.fiap.techchallenge.payment.bdd.config.SharedScenarioState;

import io.cucumber.java.Before;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class TestOrderPaymentCreationRequest {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        this.sharedScenarioState.response = null;
        RestAssured.port = port;
    }

    public SharedScenarioState sharedScenarioState;

    public TestOrderPaymentCreationRequest(SharedScenarioState sharedScenarioState) {
        this.sharedScenarioState = sharedScenarioState;
    }

    @Quando("houve solicitação para criação de uma fatura para o pedido {string}")
    public void createOrderPayment(String idOrder) {
        sharedScenarioState.response = RestAssured.given()
                .body("{\"idOrder\": " + idOrder + "}")
                .contentType("application/json")
                .post("/v1/payment/new");
    }

    @Quando("houve solicitação para criação de uma fatura sem o id do pedido")
    public void createInvalidOrderPaymentRequest() {
        sharedScenarioState.response = RestAssured.given()
                .body("{\"idOrder\": \"\"}")
                .contentType("application/json")
                .post("v1/payment/new");
    }

}
