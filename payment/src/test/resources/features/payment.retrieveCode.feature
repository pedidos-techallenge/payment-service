# Created by jubel at 10/19/24
# language: pt

Funcionalidade: Solicitação de código para pagamento

  Cenário: Código de pagamento é gerado com sucesso
    Dado um código QR para pagamento foi solicitado para o pedido 1234
    Quando o código QR para pagamento foi gerado com sucesso
    Então o código de pagamento deve ser retornado

  Cenário: Erro na geração do código de pagamento
    Dado um código QR para pagamento foi solicitado para o pedido 4321
    Quando não foi possível gerar o código QR para pagamento
    Então uma mensagem de erro deve ser retornada

    # TODO: Adicionar validação para verificar se o pagamento foi registrado