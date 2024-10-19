package org.example.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.RestAssured;

// Funcionalidade: Exibir código de pagamento
public class DisplayPaymentQRCodeTest {
    private Response response;

    @Given("o usuário acessa a página de pagamento")
    public Response o_usuario_acessa_a_pagina_de_pagamento() {
        System.out.println("o usuário acessa a página de pagamento");
        response = RestAssured.given().get("http://localhost:8080/v1/payment/1234/init");
        return response;
    }

    @When("o código QR para pagamento é gerado")
    public void o_codigo_qr_para_pagamento_e_gerado() {
        System.out.println("o código QR para pagamento é gerado");
    }

    @Then("o código de pagamento é exibido na tela")
    public void o_codigo_de_pagamento_e_exibido_na_tela() {
        System.out.println("o código de pagamento é exibido na tela");
    }
}
