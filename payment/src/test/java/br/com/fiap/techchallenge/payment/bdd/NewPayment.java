//package br.com.fiap.techchallenge.payment.bdd;
//
//import io.cucumber.java.Before;
//import io.cucumber.java.pt.Dado;
//import io.cucumber.java.pt.Então;
//import io.cucumber.java.pt.Quando;
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//public class NewPayment {
//    private static final String ENDPOINT="/v1/payment/new";
//
//    private Response response;
//
//    @LocalServerPort
//    private int port;
//
//    @Before
//    public void setUp() {
//        RestAssured.port = port;
//    }
//
//    @Dado("uma ordem de pagamento foi solicitada para o pedido {int}")
//    public void newPayment() {}
//
//    @Quando("a ordem de pagamento foi gerada com sucesso")
//    public void successOnNewPayment() {}
//
//    @Quando("houve um erro na geração da ordem de pagamento")
//    public void errorOnNewPayment() {}
//
//
//}
