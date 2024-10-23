package br.com.fiap.techchallenge.payment.infrastructure.dto;

public record QRCodeResponseDTO (String orderId, String qrCode){
}
