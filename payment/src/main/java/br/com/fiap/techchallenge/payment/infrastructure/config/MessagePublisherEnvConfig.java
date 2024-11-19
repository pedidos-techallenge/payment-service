package br.com.fiap.techchallenge.payment.infrastructure.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagePublisherEnvConfig {
    @Value("${spring.aws.region}")
    public String awsRegion;

    @Value("${spring.aws.sqs.queue.url}")
    public String queueUrl;
}
