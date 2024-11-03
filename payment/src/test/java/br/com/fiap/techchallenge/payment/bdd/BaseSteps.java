package br.com.fiap.techchallenge.payment.bdd;

import br.com.fiap.techchallenge.payment.bdd.config.SharedScenarioState;
import io.cucumber.java.pt.Dado;
import io.restassured.RestAssured;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
public class BaseSteps {
    public SharedScenarioState sharedScenarioState;

    public BaseSteps(SharedScenarioState sharedScenarioState) {
        this.sharedScenarioState = sharedScenarioState;
    }

    @Dado("o pedido {string} já possui uma fatura")
    public void orderHasPayment(String orderId) {
        RestAssured.given()
                .body("{\"orderId\": " + orderId + "}")
                .contentType("application/json")
                .post("/v1/payment/new")
                .then()
                .statusCode(200);
    }

}
