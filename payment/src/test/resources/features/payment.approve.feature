# Created by jubel at 10/19/24
# language: pt

Funcionalidade: Solicitação para faturamento de pedido

  Cenário: Fatura gerada com sucesso
    Quando houve solicitação para criação de uma fatura para o pedido 1234
    Então é retornado a mensagem "Fatura gerada com sucesso" com código 200

  Cenário: Solicitação de fatura para pedido já faturado
    Dado o pedido 2345 já possui uma fatura
    Quando houve solicitação para criação de uma fatura para o pedido 2345
    Então é retornado a mensagem "Pedido já possui uma fatura" com código 400

  Cenário: Solicitação de fatura com pedido vazio
    Quando houve solicitação para criação de uma fatura sem o id do pedido
    Então é retornado a mensagem "Número de pedido vazio" com código 400