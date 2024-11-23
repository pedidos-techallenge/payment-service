package br.com.fiap.techchallenge.payment.infrastructure.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HealthStatus {

        @SuppressWarnings("SameReturnValue")
        @GetMapping("/health")
        public String health() {
            return "OK";
        }
}
