package br.com.fiap.techchallenge.payment.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
    Client -[request code]-> Server
    Server -[generate code]-> Gateway
        Gateway -[return code]-> Server
            Server -[return code]-> Client
        Gateway -[error]-> Server
            Server -[return error]-> Client
* */
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.RestAssured;

// Funcionalidade: Exibir código de pagamento
public class RetrievePaymentCode {
    private Response response;

    @Given("um código QR para pagamento foi solicitado")
    public void qrd_code_requested() {
        System.out.println("o usuário acessa a página de pagamento");
        response = RestAssured.given().get("http://localhost:8080/v1/payment/1234/init");
    }

    @Then("a geração de código deve ser solicitada para o gateway de pagamento")
    public void code_generation_requested() {
        System.out.println("o sistema solicita a geração do código de pagamento");
    }

    @When("o código QR para pagamento foi gerado com sucesso")
    public void qrd_code_generated() {
        System.out.println("o código de pagamento é gerado com sucesso");
    }

    @Then("o código de pagamento deve ser retornado")
    public void code_returned() {
        System.out.println("o código de pagamento é retornado para o usuário");
    }

    @When("não foi possível gerar o código QR para pagamento")
    public void qrd_code_not_generated() {
        System.out.println("o código de pagamento não pode ser gerado");
    }

    @Then("uma mensagem de erro deve ser retornada")
    public void error_message_returned() {
        System.out.println("uma mensagem de erro é retornada para o usuário");
    }
}
