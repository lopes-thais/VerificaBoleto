# VerificaBoleto

Sistema desenvolvido para análise e validação de boletos,
identificando possíveis divergências entre os dados informados
pelo usuário e os dados extraídos do documento.

![Java](https://img.shields.io/badge/Java_21-red?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SPRING_BOOT_4.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&labelColor=7DBFF2&logo=docker&logoColor=white)

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
ou https://verificaboleto-m9zt.onrender.com/swagger-ui/index.html

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

## Diagrama de Arquitetura
A aplicação é estruturada em uma arquitetura baseada em uma API REST
desenvolvida com Java e Spring Boot, integrada a um front-end responsável
pela interação com o usuário.

<img width="448" height="852" alt="image" src="/docs/Diagrama-Arquitetura-VerificaBoleto.png" />

## Diagrama de sequência
<img width="762" height="382" alt="image" src="/docs/Diagrama-Sequencia-VerificaBoleto.png" />

## Estrutura de pastas 


```
VerificaBoleto/
├── src/main/java/com/thais/verificaBoleto/
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

## Melhorias Futuras

- Testes automatizados.
- Verificação de contas de concessionária (linha digitável com 48 dígitos).
- Implementação de um parser mais robusto para identificar, na String
  extraída do PDF, os valores associados aos campos "Vencimento" e
  "Valor", reduzindo a dependência de listas genéricas de valores e datas.

## Organização 
A organização do desenvolvimento do projeto foi feita a partir de KanBan no GitHub Projects, foram listados os requisitos
e funcionalidades e divisão entre Back e FrontEnd.