//package br.com.fiap.techchallenge.payment.bdd;
//
//import br.com.fiap.techchallenge.payment.bdd.config.SharedScenarioState;
//import io.cucumber.java.Before;
//import io.cucumber.java.pt.Então;
//import io.restassured.RestAssured;
//import io.restassured.module.jsv.JsonSchemaValidator;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@RunWith(SpringRunner.class)
//public class BaseSteps {
//    public SharedScenarioState sharedScenarioState;
//
//    public BaseSteps(SharedScenarioState sharedScenarioState) {
//        this.sharedScenarioState = sharedScenarioState;
//    }
//
//    @Então("o status {int} deve ser retornado")
//    public void payment_refused_message(String orderStatus) {
//        String responseStatus = sharedScenarioState.response.then()
//                .statusCode(200)
//                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/payment-approval.json"))
//                .extract()
//                .path("orderStatus");
//        assertEquals(orderStatus, responseStatus);
//    }
//}
