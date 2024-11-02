## Created by jubel at 10/29/24
## language: pt
#
#
#Funcionalidade: Aprovação ou rejeição de pagamento
#
#  Cenário: Pagamento é aprovado com sucesso
#    Dado o pedido 1234 está com o status de pagamento como "PENDING"
#    Quando o status "APPROVED" foi registrado para o pedido 1234
#    Então o status "APPROVED" deve ser retornado
#
#  Cenário: Pagamento foi recusado
#    Dado o pedido 1234 está com o status de pagamento como "PENDING"
#    Quando o status "REJECTED" foi registrado para o pedido 1234
#    Então o status "REJECTED" deve ser retornado
#
#  Cenário: Pagamento não foi encontrado
#    Dado o pedido 1234 não existe
#    Quando o status "APPROVED" foi registrado para o pedido 1234
#    Então a mensagem "Pagamento não encontrado" deve ser retornada
#
#  Cenário: Aprovação do pagamento após já ter sido aprovado
#    Dado o pedido 1234 foi registrado
#    E o pedido 1234 está com o status de pagamento como "APPROVED"
#    Quando o status "APPROVED" foi registrado para o pedido 1234
#    Então a mensagem "Pagamento já finalizado" deve ser retornada
