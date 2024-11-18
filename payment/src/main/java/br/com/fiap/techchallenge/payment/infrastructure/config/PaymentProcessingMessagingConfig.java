package br.com.fiap.techchallenge.payment.infrastructure.config;

import br.com.fiap.techchallenge.payment.infrastructure.messaging.PaymentProcessingMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentProcessingMessagingConfig {
    @Value("${spring.aws.region}")
    public String region;

    @Value("${spring.aws.sqs.queue.url}")
    public String queueUrl;
}
