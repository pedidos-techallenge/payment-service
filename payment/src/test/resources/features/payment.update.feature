# Created by jubel at 10/29/24
# language: pt


Funcionalidade: Aprovação de pagamento

  Cenário: Pagamento é aprovado com sucesso
    Dado uma aprovação de pagamento foi registrada para o pedido 1234
    Quando a ordem de pagamento está com o status "Aguardando pagamento"
    Então a mensagem "Pagamento aprovado" deve ser retornada

  Cenário: Pagamento foi recusado
    Dado uma recusa de pagamento foi registrada para o pedido 1234
    Quando a ordem de pagamento está com o status "Aguardando pagamento"
    Então a mensagem "Pagamento recusado" deve ser retornada

  Cenário: Pagamento não foi encontrado
    Dado uma aprovação de pagamento foi registrada para o pedido 1234
    Quando a ordem de pagamento não foi encontrada
    Então uma mensagem de erro deve ser registrada nos logs

  Cenário: Aprovação do pagamento após já ter sido aprovado
    Dado uma aprovação de pagamento foi registrada para o pedido 1234
    Quando a ordem de pagamento está com o status "Aguardando pagamento"
    Então uma mensagem de erro deve ser registrada nos logs
