# Created by jubel at 10/29/24
# language: pt

Funcionalidade: Verificação de status de pagamento

  Cenário: Solicitação de status de ordem de pagamento em aguardo
    Dado o status de pagamento do pedido 1234 foi solicitado
    Quando a ordem de pagamento pode ser verificada
    Então a mensagem "Aguardando pagamento" deve ser retornada

  Cenário: Solicitação de status de ordem de pagamento aprovada
    Dado o status de pagamento do pedido 1234 foi solicitado
    Quando a ordem de pagamento pode ser verificada
    Então a mensagem "Aguardando pagamento" deve ser retornada

  Cenário: Solicitação de status de ordem de pagamento recusada
    Dado o status de pagamento do pedido 1234 foi solicitado
    Quando a ordem de pagamento pode ser verificada
    Então a mensagem "Aguardando pagamento" deve ser retornada

  Cenário: Solicitação de status de ordem de pagamento não existente
    Dado o status de pagamento do pedido 1234 foi solicitado
    Quando a ordem de pagamento não está registrada na base
    Então a mensagem "Pagamento não encontrada" deve ser retornada
      E o código de erro 404 deve ser retornado