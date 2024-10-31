package br.com.fiap.techchallenge.payment.infrastructure.api;

import br.com.fiap.techchallenge.payment.adapters.controllers.PaymentProcessingController;
import br.com.fiap.techchallenge.payment.infrastructure.dto.OrderRequestDTO;
import br.com.fiap.techchallenge.payment.infrastructure.dto.PaymentStatusDTO;
import br.com.fiap.techchallenge.payment.infrastructure.dto.QRCodeResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/payment")
public class PaymentProcessingService {
    PaymentProcessingController paymentProcessingController;

    public PaymentProcessingService(PaymentProcessingController paymentProcessingController) {
        this.paymentProcessingController = paymentProcessingController;
    }

    @PostMapping("init")
    public ResponseEntity<?> getPaymentCode(@RequestBody OrderRequestDTO orderRequest) {
        try {
            String qrCode = this.paymentProcessingController.processPayment(orderRequest.orderId());
            return new ResponseEntity<>(new QRCodeResponseDTO(orderRequest.orderId(), qrCode), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Erro ao gerar código de pagamento", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("status/{orderId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String orderId) {
        try {
            String paymentStatus = this.paymentProcessingController.getPaymentStatus(orderId);
            if (paymentStatus == null) {
                return new ResponseEntity<>("Ordem de pagamento não encontrada", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new PaymentStatusDTO(orderId, paymentStatus), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Erro ao buscar status de pagamento", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
