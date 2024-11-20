package br.com.fiap.techchallenge.payment.infrastructure.config;

import br.com.fiap.techchallenge.payment.infrastructure.messaging.MessagePublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.regions.Region;

@Configuration
@Profile("!local")
public class MessagePublisherConfig {

    private final SqsClient sqsClient;

    public MessagePublisherConfig(MessagePublisherEnvConfig messagePublisherEnvConfig) {
        sqsClient = SqsClient.builder()
                .region(Region.of(messagePublisherEnvConfig.awsRegion))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    public SqsClient getSqsClient() {
        return sqsClient;
    }

    @Bean
    MessagePublisher getMessagePublisher(MessagePublisherEnvConfig messagePublisherEnvConfig, SqsClient sqsClient) {
        return new MessagePublisher(messagePublisherEnvConfig, sqsClient);
    }
}
