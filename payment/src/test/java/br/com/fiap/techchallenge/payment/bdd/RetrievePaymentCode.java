package br.com.fiap.techchallenge.payment.bdd;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

// Funcionalidade: Exibir código de pagamento
public class RetrievePaymentCode {

    private static final String ENDPOINT="http://localhost:8080/v1/payment/init";

    private Response response;

    @Dado("um código QR para pagamento foi solicitado")
    public void qrd_code_requested() {
        System.out.println("Solicitando código para pagamento da order 1234");
        response = RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"orderId\": \"1234\"}")
                .when()
                .post(ENDPOINT);
    }

    @Então("a geração de código deve ser solicitada para o gateway de pagamento")
    public void code_generation_requested() {
        System.out.println("o sistema solicita a geração do código de pagamento");
    }

    @Quando("o código QR para pagamento foi gerado com sucesso")
    public void qrd_code_generated() {
        System.out.println("o código de pagamento é gerado com sucesso");
    }

    @Então("o código de pagamento deve ser retornado")
    public void code_returned() {
        System.out.println("o código de pagamento é retornado para o usuário");
    }

    @Quando("não foi possível gerar o código QR para pagamento")
    public void qrd_code_not_generated() {
        System.out.println("o código de pagamento não pode ser gerado");
    }

    @Então("uma mensagem de erro deve ser retornada")
    public void error_message_returned() {
        System.out.println("uma mensagem de erro é retornada para o usuário");
    }
}
