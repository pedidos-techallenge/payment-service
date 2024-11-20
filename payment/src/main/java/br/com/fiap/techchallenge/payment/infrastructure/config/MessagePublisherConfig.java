package br.com.fiap.techchallenge.payment.infrastructure.config;

import br.com.fiap.techchallenge.payment.infrastructure.messaging.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagePublisherConfig {

    @Bean
    MessagePublisher getMessagePublisher(MessagePublisherEnvConfig messagePublisherEnvConfig) {
        return new MessagePublisher(messagePublisherEnvConfig);
    }
}
