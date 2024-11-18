package br.com.fiap.techchallenge.payment.infrastructure.messaging;


import br.com.fiap.techchallenge.payment.infrastructure.config.PaymentProcessingMessagingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.HashMap;
import java.util.Map;


@Service
public class PaymentProcessingMessaging {

    private final SqsClient sqsClient;

    @Autowired
    PaymentProcessingMessagingConfig paymentProcessingMessagingConfig;

    public PaymentProcessingMessaging(PaymentProcessingMessagingConfig paymentProcessingMessagingConfig) {
        this.paymentProcessingMessagingConfig = paymentProcessingMessagingConfig;
        sqsClient = SqsClient.builder()
                .region(Region.of(this.paymentProcessingMessagingConfig.region))
//                .credentialsProvider(ProfileCredentialsProvider.create())
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }

    public void sendMessage(String message) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        MessageAttributeValue messageAttributeValue = MessageAttributeValue.builder()
                .stringValue("payment")
                .dataType("String")
                .build();
        messageAttributes.put("messageType", messageAttributeValue);


        SendMessageRequest sendMessageStandadQueue = SendMessageRequest.builder()
                .queueUrl(this.paymentProcessingMessagingConfig.queueUrl)
                .messageBody(message)
                .messageAttributes(messageAttributes)
                .build();

        sqsClient.sendMessage(sendMessageStandadQueue);
        System.out.println("##### Message sent");
    }
}