package bdd;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;

// Funcionalidade: Exibir código de pagamento
public class RetrievePaymentCode {

    private static final String ENDPOINT="http://localhost:8080/v1/payment/init";

    private Response response;

    @Dado("um código QR para pagamento foi solicitado para o pedido {int}")
    public void qrd_code_requested(int orderId) {
        System.out.format(String.format("Solicitando código para pagamento do pedido %d", orderId) );
        response = RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(String.format("{\"orderId\": \"%d\"}", orderId))
                .when()
                .post(ENDPOINT);
    }

    @Quando("o código QR para pagamento foi gerado com sucesso")
    public void qrd_code_generated() {
        response.then()
                .statusCode(HttpStatus.OK.value());
        System.out.println("o código de pagamento foi gerado com sucesso");
    }

    @Então("o código de pagamento deve ser retornado")
    public void code_returned() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/QRCodeResponseSchema.json"));
        System.out.println("O código foi corretamente retornado.");
        System.out.println("Body de retorno: " + response.getBody().asString());
    }

    @Quando("não foi possível gerar o código QR para pagamento")
    public void qrd_code_not_generated() {
        response.then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        System.out.println("o código de pagamento não pode ser gerado");
    }

    @Então("uma mensagem de erro deve ser retornada")
    public void error_message_returned() {
        response.then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(equalTo("Erro ao gerar código de pagamento"));
        System.out.println("uma mensagem de erro é retornada para o usuário");
    }
}
