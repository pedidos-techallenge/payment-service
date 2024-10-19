package br.com.fiap.techchallenge.payment.infrastructure.api;

import br.com.fiap.techchallenge.payment.adapters.controllers.PaymentProcessingController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/payment")
public class PaymentProcessingService {
    PaymentProcessingController paymentProcessingController;

    public PaymentProcessingService(PaymentProcessingController paymentProcessingController) {
        this.paymentProcessingController = paymentProcessingController;
    }


    @GetMapping("{orderId}/init")
    public ResponseEntity<?> getPaymentCode(@PathVariable String orderId) {
            return new ResponseEntity<>("0001", HttpStatus.OK);
    }
}
