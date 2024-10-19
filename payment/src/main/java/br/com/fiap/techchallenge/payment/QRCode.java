package br.com.fiap.techchallenge.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("v1/payment")
public class QRCode {
    @GetMapping("{orderId}/init")
    public ResponseEntity<?> getPaymentCode(@PathVariable String orderId) {
            return new ResponseEntity<>("0001", HttpStatus.OK);
    }
}
