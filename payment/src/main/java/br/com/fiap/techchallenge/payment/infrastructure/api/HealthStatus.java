package br.com.fiap.techchallenge.payment.infrastructure.api;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HealthStatus {

        @SuppressWarnings("SameReturnValue")
        @RequestMapping("/health")
        public String health() {
            return "OK";
        }
}
