package br.com.fiap.techchallenge.payment.infrastructure.messaging;

import br.com.fiap.techchallenge.payment.adapters.gateways.IMessagePublisher;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.infrastructure.config.MessagePublisherEnvConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class MessagePublisher implements IMessagePublisher {

    private final SqsClient sqsClient;

    @Autowired
    private MessagePublisherEnvConfig messagePublisherEnvConfig;

    public MessagePublisher(MessagePublisherEnvConfig messagePublisherEnvConfig) {
        this.messagePublisherEnvConfig = new MessagePublisherEnvConfig();
        sqsClient = SqsClient.builder()
                .region(Region.of(messagePublisherEnvConfig.awsRegion))
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }

    @Override
    public void sendMessage(OrderPayment orderPayment) {
        SendMessageRequest sendMessageStandadQueue = SendMessageRequest.builder()
                .queueUrl(messagePublisherEnvConfig.queueUrl)
                .messageBody('{'
                        + "\"idOrder\": \"" + orderPayment.getOrderId() + "\","
                        + "\"statusPayment\": \"" + orderPayment.getOrderStatus() + "\""
                        + '}')
                .build();

        sqsClient.sendMessage(sendMessageStandadQueue);
    }
}
