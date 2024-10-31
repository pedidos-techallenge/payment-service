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

@RunWith(SpringRunner.class)
public class ApprovePayment {
    private static final String ENDPOINT="/v1/payment/approval";

    private Response response;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Dado("uma aprovação de pagamento foi registrada para o pedido {int}")
    public void payment_requested(int orderId) {
        System.out.println("Aprovando pagamento para o pedido " + orderId);
        response = RestAssured
                .given()
                .body(String.format("{\"orderId\": \"%d\", \"status\": \"approved\"}", orderId))
                .when()
                .post(ENDPOINT);
    }

    @Quando("a ordem de pagamento está com o status \"Aguardando pagamento\"")
    public void payment_approved() {
        // Check payment status
    }

    @Então("a mensagem \"Pagamento aprovado\" deve ser retornada")
    public void payment_approved_message() {
        response.then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/payment-approval.json"));
        System.out.println("Pagamento aprovado com sucesso");
    }

    @Dado("uma recusa de pagamento foi registrada para o pedido {int}")
    public void payment_refused(int orderId) {
        System.out.println("Recusando pagamento para o pedido " + orderId);
        response = RestAssured
                .given()
                .body(String.format("{\"orderId\": \"%d\", \"status\": \"refused\"}", orderId))
                .when()
                .post(ENDPOINT);
    }

    @Então("a mensagem \"Pagamento recusado\" deve ser retornada")
    public void payment_refused_message() {
        response.then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/payment-approval.json"));
        System.out.println("Pagamento aprovado com sucesso");
    }

    @Quando("a ordem de pagamento não foi encontrada")
    public void payment_not_found() {
        response.then()
                .statusCode(404)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/payment-not-found.json"));
    }

    @Então("uma mensagem de erro deve ser registrada nos logs")
    public void payment_not_found_message() {
        // Logged Error message
    }
}
