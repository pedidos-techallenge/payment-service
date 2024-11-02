## Created by jubel at 10/19/24
## language: pt
#
#Funcionalidade: Solicitação de código para pagamento
#
#  Cenário: Ordem de pagamento gerada com sucesso
#    Dado uma ordem de pagamento foi solicitada para o pedido 1234
#    Quando a ordem de pagamento foi gerada com sucesso
#    Então o status 200 deve ser retornado
#
#  Cenário: Erro na geração da ordem de pagamento
#    Dado uma ordem de pagamento foi solicitada para o pedido 1234
#    Quando houve um erro na geração da ordem de pagamento
#    Então o status 500 deve ser retornado
#      E a mensagem "Erro ao criar pagamento" deve ser retornada