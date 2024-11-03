package br.com.fiap.techchallenge.payment.bdd;


import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class TestOrderPaymentCreationRequest {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    private Response response;

    @Dado("o pedido {int} já possui uma fatura")
    public void orderHasPayment(int orderId) {
        RestAssured.given()
                .body("{\"orderId\": " + orderId + "}")
                .contentType("application/json")
                .post("/v1/payment/new")
                .then()
                .statusCode(200);
    }

    @Quando("houve solicitação para criação de uma fatura para o pedido {int}")
    public void createOrderPayment(int orderId) {
        response = RestAssured.given()
                .body("{\"orderId\": " + orderId + "}")
                .contentType("application/json")
                .post("/v1/payment/new");
    }

    @Quando("houve solicitação para criação de uma fatura sem o id do pedido")
    public void createInvalidOrderPaymentRequest() {
        response = RestAssured.given()
                .body("{\"orderId\": \"\"}")
                .contentType("application/json")
                .post("v1/payment/new");
    }


    @Então("é retornado a mensagem {string} com código {int}")
    public void orderPaymentCreated(String expectedMessage, int statusCode) {
        String responseMessage = response.then()
                .statusCode(statusCode)
                .extract()
                .body()
                .asString();
        assertEquals(expectedMessage, responseMessage);
    }
}
