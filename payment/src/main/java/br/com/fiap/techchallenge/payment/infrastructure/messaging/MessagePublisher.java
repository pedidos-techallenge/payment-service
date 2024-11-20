package br.com.fiap.techchallenge.payment.infrastructure.messaging;

import br.com.fiap.techchallenge.payment.adapters.gateways.IMessagePublisher;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.infrastructure.config.MessagePublisherEnvConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;


@Service
@Profile("!local")
public class MessagePublisher implements IMessagePublisher {

    private final SqsClient sqsClient;

    @Autowired
    private MessagePublisherEnvConfig messagePublisherEnvConfig;

    public MessagePublisher(MessagePublisherEnvConfig messagePublisherEnvConfig, SqsClient sqsClient) {
        this.messagePublisherEnvConfig = messagePublisherEnvConfig;
        this.sqsClient = sqsClient;
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
