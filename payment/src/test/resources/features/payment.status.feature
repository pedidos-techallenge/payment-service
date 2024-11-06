# Created by jubel at 10/19/24
# language: pt

Funcionalidade: Verificação de status da fatura

  Cenário: Verificação de status da fatura
    Dado o pedido "1234" já possui uma fatura com o status "PENDING" e o QRCode "não está" preenchido
    Quando houve a verificação de status da fatura "1234"
    Então Deve ser retornado uma resposta com o status "PENDING"
