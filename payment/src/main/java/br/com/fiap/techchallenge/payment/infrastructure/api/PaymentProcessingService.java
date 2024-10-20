package br.com.fiap.techchallenge.payment.infrastructure.api;

import br.com.fiap.techchallenge.payment.adapters.controllers.PaymentProcessingController;
import br.com.fiap.techchallenge.payment.infrastructure.dto.OrderRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/payment")
public class PaymentProcessingService {
    PaymentProcessingController paymentProcessingController;

    public PaymentProcessingService(PaymentProcessingController paymentProcessingController) {
        this.paymentProcessingController = paymentProcessingController;
    }


    @PostMapping("init")
    public ResponseEntity<?> getPaymentCode(@RequestBody OrderRequestDTO orderRequest) {
        this.paymentProcessingController.processPayment(orderRequest.orderId());
        return new ResponseEntity<>("0001", HttpStatus.OK);
    }
}
