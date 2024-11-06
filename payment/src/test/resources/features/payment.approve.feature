# Created by jubel at 10/19/24
# language: pt

Funcionalidade: Aprovação de pagamento para fatura

  Cenário: Aprovação de pagamento para fatura existente
    Dado o pedido "1234" já possui uma fatura com o status "PENDING" e o QRCode "não está" preenchido
    Quando o gateway de pagamento sinalizou a aprovação da fatura do pedido "1234"
    Então a fatura do pedido "1234" deve ser marcada com o status "APPROVED"

  Cenário: Rejeição de pagamento para fatura existente
    Dado o pedido "1234" já possui uma fatura com o status "PENDING" e o QRCode "não está" preenchido
    Quando o gateway de pagamento sinalizou a rejeição da fatura do pedido "1234"
    Então a fatura do pedido "1234" deve ser marcada com o status "REJECTED"