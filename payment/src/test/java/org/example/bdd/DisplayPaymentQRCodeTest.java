package org.example.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

// Funcionalidade: Exibir código de pagamento
public class DisplayPaymentQRCodeTest {
    @Given("o usuário acessa a página de pagamento")
    public void o_usuario_acessa_a_pagina_de_pagamento() {
        System.out.println("o usuário acessa a página de pagamento");
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
