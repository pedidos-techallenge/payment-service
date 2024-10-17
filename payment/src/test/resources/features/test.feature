Feature: Test

Scenario: Payment
     Given I have a payment
     When I pay
     Then I should have a receipt