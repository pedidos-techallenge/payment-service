package br.com.fiap.techchallenge.payment.infrastructure.api;

import br.com.fiap.techchallenge.payment.adapters.controllers.PaymentProcessingController;
import br.com.fiap.techchallenge.payment.core.usecase.entities.OrderStatus;
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

    @PostMapping("new")
    public ResponseEntity<?> createPayment(@RequestBody OrderRequestDTO orderRequest) {
        try {
            this.paymentProcessingController.createPayment(orderRequest.orderId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Erro ao criar pagamento", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("approve")
    public ResponseEntity<?> approvePayment(@RequestBody OrderRequestDTO orderRequest) {
        try {
            this.paymentProcessingController.approvePayment(orderRequest.orderId(), orderRequest.orderStatus());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Erro ao aprovar pagamento", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("qrCode/{orderId}")
    public ResponseEntity<?> getQRCode(@PathVariable String orderId) {
        try {
            String qrCode = this.paymentProcessingController.getQRCode(orderId);
            if (qrCode == null) {
                return new ResponseEntity<>("Código de pagamento não encontrado", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new QRCodeResponseDTO(orderId, qrCode), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Erro ao buscar código de pagamento", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("status/{orderId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String orderId) {
        try {
            OrderStatus paymentStatus = this.paymentProcessingController.getPaymentStatus(orderId);
            if (paymentStatus == null) {
                return new ResponseEntity<>("Ordem de pagamento não encontrada", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new PaymentStatusDTO(orderId, paymentStatus.name()), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Erro ao buscar status de pagamento", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
