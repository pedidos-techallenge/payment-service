package br.com.fiap.techchallenge.payment.bdd;

import br.com.fiap.techchallenge.payment.bdd.config.SharedScenarioState;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderPayment;
import br.com.fiap.techchallenge.payment.core.usecase.entities.PaymentStatus;
import br.com.fiap.techchallenge.payment.infrastructure.bd.MockRepository;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseSteps {
    public SharedScenarioState sharedScenarioState;

    @Autowired
    MockRepository mockRepository;

    public BaseSteps(SharedScenarioState sharedScenarioState) {
        this.sharedScenarioState = sharedScenarioState;
    }

    @Before
    public void setUp() {
        this.sharedScenarioState.response = null;
        mockRepository.clear();
    }

    @Dado("o pedido {string} já possui uma fatura com o status {string} e o QRCode {string} preenchido")
    public void orderHasPayment(String orderId, String orderStatus, String qrCodeStatus) {
        String qrCode = qrCodeStatus.equals("está") ? "QR_123456" : null;
        this.mockRepository.createPayment(new OrderPayment(orderId, PaymentStatus.valueOf(orderStatus), qrCode));
    }

    @Então("é retornado a mensagem {string} com código {int}")
    public void orderPaymentCreated(String expectedMessage, int statusCode) {
        String responseMessage = sharedScenarioState.response.then()
                .statusCode(statusCode)
                .extract()
                .body()
                .asString();
        assertEquals(expectedMessage, responseMessage);
    }
}
