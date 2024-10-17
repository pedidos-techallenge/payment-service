package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class Steps {

    @Given("I have a payment")
    public void i_have_a_payment() {
        System.out.println("I have a payment");
    }

    @When("I pay")
    public void i_pay() {
        System.out.println("I pay");
    }

    @Then("I should have a receipt")
    public void i_should_receive_a_receipt() {
        System.out.println("I should receive a receipt");
    }
}
