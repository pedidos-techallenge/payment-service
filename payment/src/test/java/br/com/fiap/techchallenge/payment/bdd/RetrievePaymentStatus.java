package br.com.fiap.techchallenge.payment.bdd;

import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@CucumberContextConfiguration
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RetrievePaymentStatus {
    private static final String ENDPOINT="/v1/payment/status";

    private Response response;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Dado("o status de pagamento do pedido 1234 foi solicitado")
    public void payment_status_requested() {
        System.out.println("Solicitando status de pagamento para o pedido 1234");
        response = RestAssured
                .given()
                .body("{\"orderId\": \"1234\"}")
                .when()
                .post(ENDPOINT);
    }

    @Quando("a ordem de pagamento pode ser verificada")
    public void payment_status_retrieved() {
        // Check if request was successful
    }

    @Então("a mensagem \"Aguardando pagamento\" deve ser retornada")
    public void payment_status_message() {
        // Check if payment status is "Aguardando pagamento"
    }

    @Quando("a ordem de pagamento não está registrada na base")
    public void payment_not_found() {
        // Check if request was successful
    }

    @Então("a mensagem \"Pagamento não encontrada\" deve ser retornada")
    public void payment_not_found_message() {
        // Check if payment status is "Pagamento não encontrada"
    }

    @Então("o código de erro 404 deve ser retornado")
    public void payment_not_found_code() {
        // Check if response status code is 404
    }
}
