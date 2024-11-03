package br.com.fiap.techchallenge.payment.bdd.config;

import br.com.fiap.techchallenge.payment.infrastructure.bd.LocalRepository;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CucumberSpringConfig {
    @Autowired
    public LocalRepository localRepository;

}