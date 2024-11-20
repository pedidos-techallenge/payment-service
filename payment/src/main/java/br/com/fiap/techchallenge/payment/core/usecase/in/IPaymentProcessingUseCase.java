package br.com.fiap.techchallenge.payment.core.usecase.in;

import br.com.fiap.techchallenge.payment.core.usecase.entities.StatusPayment;

public interface IPaymentProcessingUseCase {

    StatusPayment getStatusPayment(String idOrder);

    void createPayment(String idOrder);


    void approvePayment(String idOrder, StatusPayment statusPayment);

    String getQRCode(String idOrder);
}
