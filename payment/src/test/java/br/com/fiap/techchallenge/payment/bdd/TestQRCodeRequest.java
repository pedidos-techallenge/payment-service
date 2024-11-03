package br.com.fiap.techchallenge.payment.bdd;

import br.com.fiap.techchallenge.payment.infrastructure.bd.LocalRepository;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestQRCodeRequest {
    @LocalServerPort
    private int port;

    @Autowired
    LocalRepository localRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
        localRepository.clear();
    }


    private Response response;

    @Dado("o pedido {string} não foi registrado")
    public void orderNotFoundOnRepository(String orderId) {
        localRepository.clear();
        assertNull(localRepository.getPayment(orderId));
    }

    @Quando("foi solicitado o QR Code de pagamento para o pedido {string}")
    public void getQrCode(String orderID) {
        response = RestAssured.given()
                .get("v1/payment/qrCode/" + orderID);
    }

    @Então("o QR Code de pagamento deve ser retornado")
    public void QRCodeReturned() {
        String qrCode = response.then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/QRCodeResponseSchema.json"))
                .extract()
                .path("qrCode");

        assertFalse(qrCode.isEmpty());
    }


    @Então("é   retornado a mensagem {string} com código {int}")
    public void orderPaymentCreated(String expectedMessage, int statusCode) {
        String responseMessage = response.then()
                .statusCode(statusCode)
                .extract()
                .body()
                .asString();
        assertEquals(expectedMessage, responseMessage);
    }
}
