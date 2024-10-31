package br.com.fiap.techchallenge.payment.bdd;

import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class RetrievePaymentStatus {
    private static final String ENDPOINT="/v1/payment/status";

    private Response response;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Dado("o status de pagamento do pedido {int} foi solicitado")
    public void payment_status_requested(int orderId) {
        System.out.println("Solicitando status de pagamento para o pedido " + orderId);
        response = RestAssured
                .given()
                .when()
                .get(ENDPOINT + "/" + orderId);
    }

    @Quando("a ordem de pagamento pode ser verificada")
    public void payment_status_retrieved() {
        response.then().statusCode(200);
    }

    @Então("o status de {string} deve ser retornada")
    public void payment_status_value(String message) {
        String responseStatus = response
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/payment-status.json"))
                .extract()
                .path("orderStatus");
        assertEquals(message, responseStatus);
    }

    @Quando("a ordem de pagamento não está registrada na base")
    public void payment_not_found() {
        response.then().statusCode(404);
    }

    @Então("a mensagem {string} deve ser retornada")
    public void payment_not_found_message(String message) {
        String responseStatus = response.then()
                .assertThat()
                .extract()
                .body()
                .asString();
        assertEquals(message, responseStatus);
    }
}
