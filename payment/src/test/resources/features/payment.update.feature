# Created by jubel at 10/29/24
# language: pt


Funcionalidade: Aprovação de pagamento

  Cenário: Pagamento é aprovado com sucesso
    Dado um código de pagamento foi solicitado para o pedido 1234
      E a ordem de pagamento do pedido 1234 está com o status "Aguardando pagamento"
    Quando o pagamento é aprovado com sucesso
    Então a ordem de apgamento deve ter o status "Pagamento aprovado"

  Cenário: Pagamento foi recusado
    Dado um código QR para pagamento foi solicitado para o pedido 1234
      E a ordem de pagamento do pedido 1234 está com o status "Aguardando pagamento"
    Quando o pagamento é recusado
    Então a ordem de pagamento deve ter o status "Pagamento recusado"

  Cenário: Pagamento não foi encontrado
    Dado um código QR para pagamento foi solicitado para o pedido 1234
    Quando a ordem de pagamento não foi encontrada
    Então uma mensagem de erro deve ser registrada nos logs

  Cenário: Aprovação do pagamento após já ter sido aprovado
    Dado um código QR para pagamento foi solicitado para o pedido 1234
      E a ordem de pagamento do pedido 1234 está com o status "Pagamento aprovado"
    Quando o pagamento é aprovado
    Então uma mensagem de erro deve ser registrada nos logs
