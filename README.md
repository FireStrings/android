![ps5.png](https://bitbucket.org/repo/4naLKz/images/1051242651-ps5.png)

## **Copyright** ##
Todos os direitos reservados. O UOL é uma marca comercial do UNIVERSO ONLINE S / A. O logotipo do UOL é uma marca comercial do UNIVERSO ONLINE S / A. Outras marcas, nomes, logotipos e marcas são de propriedade de seus respectivos proprietários.
As informações contidas neste documento pertencem ao UNIVERSO ONLINE S/A. Todos os direitos reservados. UNIVERSO ONLINE S/A. - Av. Faria Lima, 1384, 6º andar, São Paulo / SP, CEP 01452-002, Brasil.
O serviço PagSeguro não é, nem pretende ser comparável a serviços financeiros oferecidos por instituições financeiras ou administradoras de cartões de crédito, consistindo apenas de uma forma de facilitar e monitorar a execução das transações de comércio electrónico através da gestão de pagamentos. Qualquer transação efetuada através do PagSeguro está sujeita e deve estar em conformidade com as leis da República Federativa do Brasil.
Aconselhamos que você leia os termos e condições cuidadosamente.


## **Aviso Legal** ##
O UOL não oferece garantias de qualquer tipo (expressas, implícitas ou estatutárias) com relação às informações nele contidas. O UOL não assume nenhuma responsabilidade por perdas e danos (diretos ou indiretos), causados por erros ou omissões, ou resultantes da utilização deste documento ou a informação contida neste documento ou resultantes da aplicação ou uso do produto ou serviço aqui descrito. O UOL reserva o direito de fazer qualquer tipo de alterações a quaisquer informações aqui contidas sem aviso prévio.

Biblioteca de integração PagSeguro para Android
===============================================

Descrição
---------

Biblioteca do PagSeguro para utilização na plataforma Android.
A lib facilita o processo de checkout via PagSeguro dentro da plataforma Android, garantindo um fluxo de checkout transparente para o usuário final. 

Esta LIB é um jar simples que só funciona no ambiente Android, portanto, embora seja JAR não é possivel utilizá-la em um ambiente JAVA SE ou mesmo JAVA EE.

Requisitos para utilizar a LIB em seu projeto Android
-----------------------------------------------------

 - [Android] 3.0.+ (SDK Version 11 ou superior)
 - [GSon]
 - Configurar a dependência do GSON + Lib PagSeguro (via Maven, gradle ou download)


Instalação
----------

Baixe o [último JAR][1] ou obtenha via Maven:
```xml
<repositories>
  <repository>
    <id>pagseguro-github</id>
    <name>Maven Repository PagSeguro</name>
    <layout>default</layout>
    <url>https://raw.githubusercontent.com/everton-rosario/android/mvn-repo/</url>
  </repository>
</repositories>
  
<dependency>
    <groupId>br.com.uol.ps</groupId>
    <artifactId>library</artifactId>
    <version>0.7</version>
</dependency>
```

Ou configure via Gradle:

```gradle
...
repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url "https://raw.github.com/pagseguro/android/mvn-repo/"
    }
}

...

dependencies {
    ...
    compile 'br.com.uol.ps:library:0.7'
    compile 'com.google.code.gson:gson:+'
    ...
}
```

Exemplo de Uso
--------------

Permissões necessárias para a Lib funcionar.
Adicionar no Android-Manifest.xml
```xml
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.READ_PHONE_STATE" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```


Chamada de pagamento a ser adicionada:
```java
  PagSeguro.pay(new PagSeguroRequest().withNewItem("Nome do item", 1.0, new BigDecimal(1.00))   // Nome do item, quantidade do item, valor unitário do item
                                      .withVendorEmail("SEU_EMAIL@NO_PAGSEGURO.COM")             // Email do vendedor, deverá ser igual ao email da autenticacao
                                      .withBuyerEmail("comprador@mail.com.br")                  // Email do comprador caso possua
                                      .withBuyerCellphoneNumber("5511992190364")                // Telefone do comprador
                                      .withReferenceCode("123")                                 // Codigo que é utilizado apenas pelo vendedor, para referencia de transação
                                      .withEnvironment(PagSeguro.Environment.PRODUCTION)        // Ambiente que será usado: PRODUCTION, MOCK_SUCCESS ou MOCK_ERROR
                                      .withAuthorization("SEU_EMAIL@NO_PAGSEGURO.COM", "codigo obtido na home do pagseguro, dentro do seu ibank"),
                getActivity(),
                R.id.container,                                                                 // Id do fragment/view onde serão desenhadas as telas de checkout
                new PagSeguro.PagSeguroListener() {
      @Override
      public void onSuccess(PagSeguroResponse response, Context context) {
          Toast.makeText(context, "Lib PS retornou pagamento aprovado! Resposta: " + response, Toast.LENGTH_LONG).show();
      }
  
      @Override
      public void onFailure(PagSeguroResponse response, Context context) {
          Toast.makeText(context, "Lib PS retornou FALHA no pagamento! Resposta:" + response, Toast.LENGTH_LONG).show();
      }
  });
```


A adição da Lib e o fluxo de checkout são desenhados dinamicamente na tela do aplicativo em que esteja embarcada. Portanto é necessário enviar o ID do container que deverá ser um Fragment.

Verifique o exemplo antes de alterar seu aplicativo:
```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/container"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    tools:ignore="MergeRootFrame" />
```

Executando a Loja Modelo (Exemplo)
----------------------------------

Para executar o projeto exemplo:
- Fazer clone do repositorio do github
- Importar na sua IDE (Android Studio (1.0.0) ou Eclipse)
- Executar o modulo "app"


Chaveando entre os Ambientes para testes e Produção
--------------------------------------------------
Atualmente existem os seguintes Environments (ambientes) para execução da LIB:
- Environment.PRODUCTION
- Environment.MOCK_SUCCESS
- Environment.MOCK_ERROR

PRODUCTION: Ambiente que utiliza dinheiro real e cartões de créditos reais. Para efetuar um pagamento com sucesso utilize cartões reais, e contas de Vendedor que estejam devidamente aprovadas no PagSeguro

MOCK_SUCCESS: Ambiente que não chega a fazer conexão com o PagSeguro, porém o fluxo acontece 100% como em produção. A resposta sempre será de uma compra aprovada, com código de transação (aaaabbbbccccddddeeeeffffgggghhhh) e como se tivesse sido utilizado um cartão visa para o pagamento.

MOCK_ERROR: Da mesma forma como um pagamento utilizando o MOCK_SUCCESS, porém ao final do fluxo de pagamento a resposta final sempre será de erro no pagamento.


Dicas e Macetes
---------------
Somente com este jar, sem nenhuma configuração de telas ou Lib, o fluxo de pagamento funcionará. Esta biblioteca desenha no Fragment informado via parâmetro tudo que é necessário para que o fluxo de checkout ocorra de forma menos intrusiva possível, facilitando assim a implementação.

A taxa de conversão com certeza será maior para o fluxo onde o usuário do seu aplicativo tenha instalado e logado no aplicativo Carteira do PagSeguro: https://play.google.com/store/apps/details?id=br.com.uol.ps.wallet, pois o fluxo de pagamento fica em apenas 2 passos: Clicar em pagar e selecionar o cartão já previamente cadastrado no PagSeguro e PRONTO! Em futuras versões melhoraremos ainda mais este fluxo para que o pagamento de seu produto/serviço seja o mais fácil possível de o usuário pagar e assim mais conversões de venda para seu negócio.


Dúvidas?
----------
---
Caso tenha dúvidas ou precise de suporte, acesse nosso [fórum].


FAQ
---------------
---
<b>Qual as especificações minimas para utilização no Android?</b>
<br>[Android] 3.0.+ (SDK Version 11 ou superior)
<br>Configurar a dependência do GSON + Lib PagSeguro (via Maven, gradle ou download)

<b>O jar pode ser utilizado em qualquer projeto?</b>
<br>
Esta LIB é um jar simples que só funciona no ambiente Android, portanto, embora seja JAR não é possivel utilizá-la em um ambiente JAVA SE ou mesmo JAVA EE.

<b>É possivel realizar parcelamento?</b>
<br>No momento nossa solução não abrange tal recurso, em futuras implementações pretendemos adicionar o recurso, iremos melhorar cade vez mais a solução enriquecendo e facilitando a sua utilização.

<b>Estou com um problema, Porque o valor da venda sempre esta sendo passado com R$ 1,00?</b>
<br>
Caso esteja com esse problema, atualize a versão da lib para 0.7v pois esse problema foi corrigido, a versão 0.6v apresentava essa falha. (Consulte o changelog na documentação com caracteristicas das versões)


Changelog
---------
0.7
 - Correções de bugs
 - Ajuste componente NumberPicker
 - Melhorias no aplicativo de exemplo (permite inserir valor da venda)
  
0.6
 - Versão compatível com Lollipop 5.0.1 (#21)
 - Migrada estrutura do projeto para Android Studio 1.0.0
 - Reconstruido componente de DatePicker para expiração para ficar compatível com Lollipop.

0.5
 - Mascara para input do CPF e validação de CPF que não permite o uso com CPFs inválidos em qualquer Environment.
 - Nova classe de resposta para os requests de pagamento. PagSeguroResponse. Nela estão todas as informações acerca do pagamento que pode ter sido ou não efetuado.
 - Fluxo APP-2-APP melhorado para garantir sucesso de mais transações

0.4
 - Novo layout para seleção de data de expiração que utiliza o padrão Android
 - Limpeza de código

0.3
 - CVV opcional de acordo com configuração no backend do PagSeguro

0.2
 - Versão com conexão para o Backend de produção do PagSeguro
 - Adicionados mecanismos de token para autorização da app/usuário
 - Lib armazena estado durante a troca de contexto ou recriação da activity na troca de orientação do device
 - Adicionado tratamento para lista de itens: .withNewItem("Nome do item", 1.0, new BigDecimal(1.00))

0.1
 - Versão inicial com contratos de interface iniciais
 - Não existe conexão com o servidor do PagSeguro


Licença
-------

Copyright 2014 PagSeguro Internet LTDA.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.


Notas
-----

 - O PagSeguro somente aceita pagamento utilizando a moeda Real brasileiro (BRL).
 - Certifique-se que o email e o token informados estejam relacionados a uma conta que possua o perfil de vendedor ou empresarial.
 - Certifique-se que tenha definido corretamente o charset de acordo com a codificação (ISO-8859-1 ou UTF-8) do seu sistema. Isso irá prevenir que as transações gerem possíveis erros ou quebras ou ainda que caracteres especiais possam ser apresentados de maneira diferente do habitual.
 - Para que ocorra normalmente a geração de logs, certifique-se que o diretório e o arquivo de log tenham permissões de leitura e escrita.


[Dúvidas?]
----------

Em caso de dúvidas mande um e-mail para desenvolvedores@pagseguro.com.br


Contribuições
-------------

Achou e corrigiu um bug ou tem alguma feature em mente e deseja contribuir?

* Faça um fork.
* Adicione sua feature ou correção de bug.
* Envie um pull request no [GitHub].

  [1]: https://raw.githubusercontent.com/pagseguro/android/mvn-repo/br/com/uol/ps/library/0.7/library-0.7.jar
  [Android]: http://www.android.com/
  [GSon]: https://code.google.com/p/google-gson/
  [fórum]: http://forum.pagseguro.uol.com.br/
  [GitHub]: https://github.com/pagseguro/php/
  
  
  **UOL - O melhor conteúdo**

© 1996-2015 O melhor conteúdo. Todos os direitos reservados.
UNIVERSO ONLINE S/A - CNPJ/MF 01.109.184/0001-95 - Av. Brigadeiro Faria Lima, 1.384, São Paulo - SP - CEP 01452-002 
* **
  
 

