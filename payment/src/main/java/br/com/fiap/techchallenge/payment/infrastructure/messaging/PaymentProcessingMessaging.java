import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;



public class PaymentProcessingMessaging {

    private final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    @Value("${sqs.queue.url}")
    private final String queueUrl;

    public void sendPaymentProcessingMessage(String orderId) {
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(orderId)
                .withDelaySeconds(5);
        sqs.sendMessage(send_msg_request);
    }
}