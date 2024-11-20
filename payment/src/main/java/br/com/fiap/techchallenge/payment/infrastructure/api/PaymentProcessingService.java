package br.com.fiap.techchallenge.payment.infrastructure.api;

import br.com.fiap.techchallenge.payment.adapters.controllers.PaymentProcessingController;
import br.com.fiap.techchallenge.payment.core.usecase.entities.StatusPayment;
import br.com.fiap.techchallenge.payment.infrastructure.dto.OrderRequestDTO;
import br.com.fiap.techchallenge.payment.infrastructure.dto.StatusPaymentDTO;
import br.com.fiap.techchallenge.payment.infrastructure.dto.QRCodeResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/payment")
public class PaymentProcessingService {
    final PaymentProcessingController paymentProcessingController;


    public PaymentProcessingService(PaymentProcessingController paymentProcessingController) {
        this.paymentProcessingController = paymentProcessingController;
    }

    @PostMapping("new")
    public ResponseEntity<?> createPayment(@RequestBody OrderRequestDTO orderRequest) {
        try {
            this.paymentProcessingController.createPayment(orderRequest.idOrder());
            return new ResponseEntity<>("Fatura gerada com sucesso", HttpStatus.OK);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            if (e.getMessage() != null) {
                if (e.getMessage().equals("Order already has a payment")) {
                    return new ResponseEntity<>("Pedido já possui uma fatura", HttpStatus.BAD_REQUEST);
                } else if (e.getMessage().equals("Empty idOrder provided")) {
                    return new ResponseEntity<>("Número de pedido vazio", HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>("Erro ao criar pagamento", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("qrCode/{idOrder}")
    public ResponseEntity<?> getQRCode(@PathVariable String idOrder) {
        try {
            String qrCode = this.paymentProcessingController.getQRCode(idOrder);
            if (qrCode == null) {
                return new ResponseEntity<>("Código de pagamento não encontrado", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new QRCodeResponseDTO(idOrder, qrCode), HttpStatus.OK);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            if (e.getMessage() != null) {
                if (e.getMessage().equals("Order not found")) {
                    return new ResponseEntity<>("Pedido não encontrado", HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>("Erro ao buscar código de pagamento", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("approve")
    public ResponseEntity<?> approvePayment(@RequestBody OrderRequestDTO orderRequest) {
        try {
            this.paymentProcessingController.approvePayment(orderRequest.idOrder(), orderRequest.statusPayment());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Erro ao aprovar pagamento", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("status/{idOrder}")
    public ResponseEntity<?> getStatusPayment(@PathVariable String idOrder) {
        try {
            StatusPayment statusPayment = this.paymentProcessingController.getStatusPayment(idOrder);
            if (statusPayment == null) {
                return new ResponseEntity<>("Ordem de pagamento não encontrada", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new StatusPaymentDTO(idOrder, statusPayment.name()), HttpStatus.OK);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Erro ao buscar status de pagamento", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
