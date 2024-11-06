# Created by jubel at 10/19/24
# language: pt

Funcionalidade: Solicitação de QR Code para pedido faturado

  Cenário: Solicitação do QR Code para pedido existente
    Dado o pedido "1234" já possui uma fatura com o status "PENDING" e o QRCode "está" preenchido
    Quando foi solicitado o QR Code de pagamento para o pedido "1234"
    Então o QR Code de pagamento deve ser retornado


  Cenário: Solicitação de QR Code para pedido inexistente
    Dado o pedido "1234" não foi registrado
    Quando foi solicitado o QR Code de pagamento para o pedido "1234"
    Então é retornado a mensagem "Pedido não encontrado" com código 404


