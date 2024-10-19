# Created by jubel at 10/19/24
# language: pt

Funcionalidade: Exibir código de pagamento

  Cenario: Exibir código QR para pagamento
    Dado o usuário acessa a página de pagamento
    Quando o código QR para pagamento é gerado
    Então o código de pagamento é exibido na tela