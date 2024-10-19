# Created by jubel at 10/19/24
# language: pt

Funcionalidade: Solicitação de código para pagamento

  Cenário: Solicitação de código QR para pagamento
    Dado um código QR para pagamento foi solicitado
    Então a geração de código deve ser solicitada para o gateway de pagamento

  Cenário: Código de pagamento é gerado com sucesso
    Dado um código QR para pagamento foi solicitado
    Quando o código QR para pagamento foi gerado com sucesso
    Então o código de pagamento deve ser retornado

  Cenário: Erro na geração do código de pagamento
    Dado um código QR para pagamento foi solicitado
    Quando não foi possível gerar o código QR para pagamento
    Então uma mensagem de erro deve ser retornada