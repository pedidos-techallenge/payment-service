//package br.com.fiap.techchallenge.payment.bdd;
//
//import br.com.fiap.techchallenge.payment.bdd.config.SharedScenarioState;
//import io.cucumber.java.Before;
//import io.cucumber.java.pt.Dado;
//import io.cucumber.java.pt.Então;
//import io.cucumber.java.pt.Quando;
//import io.restassured.RestAssured;
//import io.restassured.module.jsv.JsonSchemaValidator;
//import io.restassured.response.Response;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@RunWith(SpringRunner.class)
//public class ApprovePayment {
//    private static final String ENDPOINT="/v1/payment";
//
//    private SharedScenarioState sharedScenarioState;
//
//    public ApprovePayment(SharedScenarioState sharedScenarioState) {
//        this.sharedScenarioState = sharedScenarioState;
//    }
//
//    @LocalServerPort
//    private int port;
//
//    @Before
//    public void setup() {
//        RestAssured.port = port;
//    }
//
//    @Dado("o pedido {int} está com o status de pagamento como {string}")
//    public void givenStatusForPaymentIs(int orderId, String paymentStatus) {
//        String responseStatus = RestAssured.given()
//                .get(ENDPOINT + "/status/" + orderId)
//                .path("orderStatus");
//        assertEquals(responseStatus, paymentStatus);
//    }
//
//    @Quando("o status {string} foi registrado para o pedido {int}")
//    public void whenRegisterNewStatusForPayment(String orderStatus, int orderId) {
//        // Check payment status
//    }
//
//    @Dado("o pedido {int} não existe")
//    public void givenOrderDoesNotExist(int orderId) {
//        Response response = RestAssured.given()
//                .get(ENDPOINT + "/status/" + orderId);
//        response.then()
//                .statusCode(404);
//    }
//
//
//    @Dado("uma recusa de pagamento foi registrada para o pedido {int}")
//    public void payment_refused(int orderId) {
//        System.out.println("Recusando pagamento para o pedido " + orderId);
//        sharedScenarioState.response = RestAssured
//                .given()
//                .body(String.format("{\"orderId\": \"%d\", \"status\": \"REJECTED\"}", orderId))
//                .when()
//                .post(ENDPOINT);
//    }
//
//
//    @Quando("a ordem de pagamento não foi encontrada")
//    public void payment_not_found() {
//        sharedScenarioState.response.then()
//                .statusCode(404)
//                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/payment-not-found.json"));
//    }
//}
