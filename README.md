# VerificaBoleto

Sistema desenvolvido para análise e validação de boletos,
identificando possíveis divergências entre os dados informados
pelo usuário e os dados extraídos do documento.

![Java](https://img.shields.io/badge/Java_21-red?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SPRING_BOOT_4.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&labelColor=7DBFF2&logo=docker&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit_5_Tests-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![SLF4J Logging](https://img.shields.io/badge/SLF4J_Logging-00599C?style=for-the-badge&logo=logstash&logoColor=white)
![Gemini API](https://img.shields.io/badge/Google_Gemini_AI-8E75B2?style=for-the-badge&logo=googlegemini&logoColor=white)
![AI Agent](https://img.shields.io/badge/Agente_de_IA-Active-blueviolet?style=for-the-badge&logo=openai&logoColor=white)
## O problema

Segundo dados do [G1](https://g1.globo.com/politica/noticia/2025/08/14/golpe-pix-boleto-falso-datafolha-fbsp.ghtml), entre 2024 e 2025 cerca de 24 milhões de pessoas foram vítima de golpes envolvendo boletos bancários ou pix. 
Nesse mesmo período, o prejuízo agregado gerado por esse tipo de golpe foi de quase R$29 bilhões.
Esses números evidenciam a necessidade urgente de medidas capazes de mitigar a incidência desse tipo de fraude. Entre elas, destacam-se os golpes envolvendo boletos bancários, nos quais o criminoso pode criar um documento visualmente idêntico ao original, mas alterar os dados associados à linha digitável para direcionar o pagamento à sua própria conta. Como a vítima muitas vezes não confere essas informações antes de efetuar o pagamento, o valor pode ser destinado diretamente ao golpista, mesmo que o documento apresentado aparente ser legítimo.

## A solução
Pensando nesse contexto, desenvolvi o VerificaBoleto. O intuito do sistema é ser uma ferramenta de apoio na verificação de Boletos Bancários.

O usuário insere os principais dados, ou o boleto em PDF, e o sistema utiliza as regras de cálculo de dígitos verificadores dos boletos bancários, incluindo os módulos 10 e 11, 
para verificar se a linha digitável é matematicamente válida, calcular o DV geral e extrair campos como banco emissor, data de vencimento e valor.
Por fim, os dados extraídos são comparados com os informados e o sistema retorna para o usuário se existe alguma divergência e mostra quais.
Auxiliando assim, a qualquer pessoa verificar um boleto e encontrar inconsistências mesmo sem conhecimentos prévios sobre como encontrar cada campo na linha.

## Como funciona
O usuário pode:

- Informar os dados manualmente;
- Enviar um arquivo PDF contendo o boleto.

O sistema realiza:

- Extração dos dados do documento (quando enviado em PDF);

- Extração dos dados presentes na linha digitável; 

- Comparação dos dados informados com os dados cadastrados na linha; 

- Classificação dos dados do boleto como: 

* ✅ Dados Consistentes
* ⚠️ Dados Inconsistentes

Além disso, são apresentados os campos divergentes identificados durante a análise.

## Tecnologias Utilizadas

## BackEnd
* Java 21
* Spring Boot

## FrontEnd

* HTML
* CSS 
* JavaScript

[Link repositório FrontEnd](https://github.com/lopes-thais/FrontEnd-VerificaBoleto/tree/main)

## Infraestrutura

* Render
* Docker

## Funcionalidades

* Extração de dados do arquivo PDF do boleto via PDFBox
* Verificação de boletos por meio da linha digitável;
* Validação da linha a partir dos módulos 10 e 11;
* Extração de código de barras de 44 dígitos a partir da linha digitável;
* Upload e extração automática de informações de boletos em PDF;
* Extração de data de vencimento, banco e valor do boleto a partir da linha digitável;
* Exibição dinâmica dos resultados da análise;
* Comparação visual entre dados informados e dados extraídos;
* Exibição comparativa dos dados divergentes;
* Rastreabilidade e Observabilidade: Logs estruturados com `SLF4J/Logback` 
para acompanhamento de cada etapa do fluxo de verificação e alertas de erros.
*Agente de IA (Gemini) para encontrar valor e data de vencimento dos boletos em PDF.

## Como executar 

Pré-requisitos:
- Docker

O projeto possui um `Dockerfile` utilizando um build multi-stage.
A primeira etapa é responsável pela compilação da aplicação com Maven
e JDK 21. A segunda utiliza apenas o JRE 21 para executar o `.jar`
gerado.

Para construir a imagem:

```bash  
docker build -t verificaboleto . 
```

Para executar o container:
```bash
docker run -p 8081:8081 verificaboleto
```

A aplicação estará disponível em:
http://localhost:8081

A documentação da API pode ser acessada pelo Swagger UI:
http://localhost:8081/swagger-ui/index.html

### Estratégia de build

O Dockerfile utiliza um **multi-stage build**, separando o processo
de compilação da execução da aplicação.

- **Build:** Maven + Eclipse Temurin JDK 21;
- **Runtime:** Eclipse Temurin JRE 21 Alpine;
- Apenas o `.jar` gerado é copiado para a imagem final.

Essa abordagem reduz o conteúdo desnecessário presente na imagem de
execução e separa as responsabilidades de build e runtime.

## Como utilizar

Acesse a documentação Swagger em:

http://localhost:8081/swagger-ui/index.html

ou 

https://verificaboleto-m9zt.onrender.com/swagger-ui/index.html

### Enviando os dados manualmente

Selecione POST e em seguida /boleto/verificar.
Insira os dados via JSON seguindo o padrão do exemplo a seguir.
No campo banco, informe o código do banco (por exemplo, 341 para Itaú).
A linha digitável abaixo é apenas como exemplo, utilize uma do seu acesso.

````bash
{
    "linhaDigitavel": "00190000090262656000400000000000100000000000000",
    "dataVencimento": "2026-07-28",
    "banco": "341",
    "valor": 2138.84  
}
````

### Enviando o arquivo em PDF do Boleto

São aceitos arquivos em formato PDF contendo o boleto. O sistema realiza a extração do texto do documento e 
utiliza as informações encontradas durante a análise.
Selecione POST e em seguida /boleto/pdf.
Anexe o arquivo e clique em executar.

### Exemplo de resultado esperado

````bash
Response body
{
  "mensagem": "Dados do boleto consistentes com a linha digitável.",
  "status": "CONSISTENTE",
  "verificacoes": [
    {
      "campo": "Banco",
      "mensagem": "Sem divergências.",
      "ok": true,
      "valorExtraido": "341 - Itaú",
      "valorInformado": "341 - Itaú"
    },
    {
      "campo": "Valor",
      "mensagem": "Sem divergências.",
      "ok": true,
      "valorExtraido": "2138.84",
      "valorInformado": "2138.84"
    },
    {
      "campo": "Vencimento",
      "mensagem": "Sem divergências.",
      "ok": true,
      "valorExtraido": "2026-07-28",
      "valorInformado": "2026-07-28"
    }
  ]
}
````

## Agente de IA (Gemini)

Uma das maiores dificuldades da verificação de boletos em PDF era encontrar corretamente a data de vencimento e o valor do documento corretamente, 
pois as instituições financeiras muitas vezes informam esses dados de forma diferente entre si. Por exemplo, uma pode colocar como "Vencimento" e outra como "Pagar até".

Para cobrir essas diferenças seria necessário um parser com muitos padrões cadastrados, o que demandaria muito código e tempo e ainda assim não cobriria todos os casos.
Pensando em facilitar essa parte da verificação, desenvolvi um agente de IA através da API do Gemini.

Além disso, uma das minhas preocupações foi o envio de dados sensíveis do usuário que poderiam estar presentes no PDF do boleto.

Para evitar isso, criei um extrator de trechos, que através do regex encontra padrões de data e valores monetários e extrai somente 15 caracteres antes e depois dos padrões. 

Enviando assim, somente os dados extritamente necessários para encontrar o valor e o vencimento do boleto.

Por fim, caso o Agente esteja indisponível, o sistema faz um FallBack para o parser do sistema extrair as datas e valores e armazenar em uma lista para comparação.

Você pode saber mais sobre o Agente de IA aqui:

[Agente de IA do VerificaBoleto](https://github.com/lopes-thais/VerificaBoleto/tree/feature/agente-ia-pdf)

## Suíte de Testes Unitários

A qualidade e a integridade matemática do sistema são garantidas por uma suíte completa de **testes unitários com JUnit 5**, seguindo as convenções de execução do **Maven Surefire Plugin**.

### Cobertura e estrutura dos testes

Os testes cobrem tanto os cenários felizes (*happy path*) quanto os cenários de divergência, erro de digitação e validação de margem de tolerância financeira:

```text
src/test/java/com/thais/verificaBoleto/testesUnitarios/
├── modulo10/
│   ├── LinhaValidaTest.java                # Validação do DV dos campos 1, 2 e 3
│   └── LinhaInvalidaTest.java              # Captura de erro em DVs incorretos
├── modulo11/   
│   ├── LinhaValidaTest.java                # Cálculo do DV Geral do código de barras (43 dígitos)
│   └── LinhaInvalidaTest.java              # Rejeição de DV Geral inconsistente
├── montadorCodigoBarras/   
│   ├── MontarCodigoLinhaValidaTest.java   
│   └── MontarCodigoLinhaInvalidaTest.java
├── parserLinha/   
│   ├── campos/                             # Parsing dos campos isolados (Campo 1, 2 e 3)
│   ├── dados/                              # Extração dos dados banco, valor, vencimento e moeda
│   └── linha/                              # Tratamento de linhas com menos dígitos ou caracteres
└── comparadorService/   
    ├── ConsistenteManualTest.java          # Validação de dados informados sem divergência
    ├── InconsistenteManualTest.java        # Identificação de divergência em valores/vencimento
    ├── ConsistentePdfTest.java             # Comparação bem-sucedida dos dados extraídos via PDF
    └── InconsistentePdfTest.java           # Alertas de discrepância entre PDF e Linha Digitável
```

### Como executar os Testes Unitários

Para executar a suíte completa de testes via terminal:

```bash
./mvnw test
```

### Exemplo de output esperado no terminal

```bash
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.thais.verificaBoleto.testesUnitarios.modulo10.LinhaValidaTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
...
[INFO] -------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] -------------------------------------------------------
```

## Diagrama de Arquitetura
A aplicação é estruturada em uma arquitetura baseada em uma API REST
desenvolvida com Java e Spring Boot, integrada a um front-end responsável
pela interação com o usuário.

### Fluxo verificação dados informados pelo usuário
<img width="448" height="852" alt="image" src="/docs/fluxoManual.png" />

### Fluxo verificação boleto em PDF
<img width="762" height="382" alt="image" src="/docs/FluxoPdf.png" />

## Estrutura de pastas 


```text
VerificaBoleto/
├── src/main/java/com/thais/verificaBoleto/
│   ├──agentePdf
│   ├── dto/
│   │   ├──AgenteRequest
│   │   └──AgenteResponse
│   ├── parser/
│   │   └──ExtracaoTrechos
│   ├── service/
│   │   └──AgenteService
│   │└──GeminiClient   
│   ├── config/
│   │   └── OpenApiConfig
│   ├── controller/
│   │   ├── BoletoController
│   │   └── HomeController
│   ├── dto/
│   │   ├── BoletoRequest
│   │   ├── BoletoResponse
│   │   ├── DadosPdf
│   │   ├── LinhaParseada
│   │   ├── ProblemaErroResponse
│   │   └── VerificacaoResponse
│   ├── enums/
│   │   ├── Banco
│   │   └── StatusVerificacao
│   ├── exception/
│   │   └── GlobalExceptionHandler
│   ├── parser/
│   │   └── ExtratorDadosPdf
│   ├── service/
│   │   ├── BoletoService
│   │   ├── ComparadorService
│   │   └── PdfService
│   └── validator/
│       ├── Modulo10
│       ├── Modulo11
│       └── MontadorCodigoBarras
├── Dockerfile
└── README.md
```

## Documentação da API

A API do VerificaBoleto é documentada utilizando Swagger/OpenAPI, permitindo visualizar os endpoints disponíveis, seus parâmetros, requisições e respostas, além de possibilitar a realização de testes diretamente pela interface do Swagger UI.

A documentação pode ser acessada em:

[Swagger UI](https://verificaboleto-m9zt.onrender.com/swagger-ui/index.html)

## Limitações

O verificaBoleto não verifica ou valida:

- CPF/CNPJ do beneficiário.
- Boletos de cobrança com linha digitável fixa, nos quais o valor é
  definido ou atualizado no momento do pagamento;
- A validação matemática não garante que o beneficiário seja legítimo;
- O sistema não consulta diretamente instituições financeiras para confirmar o beneficiário;
- Não verifica linhas digitáveis com mais de 47 dígitos. 

## Desafios Técnicos

- Implementação do montador de código de barras para utilização no Módulo 11;
- Tratamento de diferentes estruturas e regras de validação;
- Implementação de captura e tratamento de casos de exceção;
- Extração de informações estruturadas a partir do texto bruto de PDFs.
- Correção de inconsistências relacionadas à comparação de tipos durante
  o tratamento de resultados de validação;
- Implementação de uma regra de tolerância para divergências de até um dia
  na data de vencimento.
- Implementação de logging estruturado com `SLF4J` nos serviços principais (`BoletoService`, `PdfService`).

## Melhorias Futuras

- Verificação de contas de concessionária (linha digitável com 48 dígitos).
- Implementação de um parser mais robusto para identificar, na String
  extraída do PDF, os valores associados aos campos "Vencimento" e
  "Valor", reduzindo a dependência de listas genéricas de valores e datas e evitar dependência do Agente de IA.

## Organização 
A organização do desenvolvimento do projeto foi feita a partir de KanBan no GitHub Projects, foram listados os requisitos
e funcionalidades e divisão entre Back e FrontEnd.