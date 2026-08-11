# Boleto Verify
## O problema

Segundo dados do G1, entre 2024 e 2025 cerca de 24 milhões de pessoas foram vítima de golpes envolvendo boletos bancários ou pix. 
Nesse mesmo período, o prejuízo agregado gerado por esse tipo de golpe foi de quase R$29 bilhões.
Esses números evidenciam a necessidade urgente de medidas capazes de mitigar a incidência desse tipo de fraude. Entre elas, destacam-se os golpes envolvendo boletos bancários, nos quais o criminoso pode criar um documento visualmente idêntico ao original, mas alterar os dados associados à linha digitável para direcionar o pagamento à sua própria conta. Como a vítima muitas vezes não confere essas informações antes de efetuar o pagamento, o valor pode ser destinado diretamente ao golpista, mesmo que o documento apresentado aparente ser legítimo.

## O sistema
Pensando nesse contexto, desenvolvi o Boleto Verify. O intuito do sistema é ser uma ferramenta de apoio na verificação de Boletos Bancários.

O usuário insere os principais dados, ou o boleto em PDF, e o sistema utiliza as regras de cálculo de dígitos verificadores dos boletos bancários, incluindo os módulos 10 e 11, para verificar se a linha digitável é matematicamente válida, calcular o DV geral e extrair campos como banco emissor, fator de vencimento e valor.
Por fim, os dados extraídos são comparados com os informados e o sistema retorna para o usuário se existe alguma divergência e mostra quais.
Auxiliando assim, a qualquer pessoa verificar um boleto e encontrar inconsistências mesmo sem conhecimentos prévios sobre como encontrar cada campo na linha.

# Como funciona
O usuário pode:

- Informar os dados manualmente;
- Enviar um arquivo PDF contendo o boleto.

O sistema realiza:

-Extração dos dados do documento (quando enviado em PDF);

-Extração dos dados presentes na linha digitável; 

-Comparação dos dados informados com os dados cadastrados na linha; 

-Classificação dos dados do boleto como: 

* ✅ Dados Consistentes
* ⚠️ Dados Inconsistentes

Além disso, são apresentados os campos divergentes identificados durante a análise.

## Tecnologias Utilizadas

## Front-End
* HTML5
* CSS3
* JavaScript

## Back-End
* Java 21
* Spring Boot

## Infraestrutura

* Render
* GitHub/develop

## Organização

A organização do desenvolvimento do projeto foi feita através de Kanban no GitHub Projects. 

Além disso, foram feitos levantamento de regras de negócio, requisitos, funcionalidades, e prototipagem das telas pelo Whimsical e Figma.

# Funcionalidades

* Extração de dados do arquivo PDF do boleto via PDFBox
* Verificação de boletos por meio da linha digitável;
* Upload e extração automática de informações de boletos em PDF;
* Extração de data de vencimento, banco e valor do boleto a partir da linha digitável;
* Exibição dinâmica dos resultados da análise;
* Comparação visual entre dados informados e dados extraídos;
* Exibição comparativa dos dados divergentes;

## Documentação da API

A API do Boleto Verify é documentada utilizando Swagger/OpenAPI, permitindo visualizar os endpoints disponíveis, seus parâmetros, requisições e respostas, além de possibilitar a realização de testes diretamente pela interface do Swagger UI.

A documentação pode ser acessada em:

[Swagger UI](http://localhost:8081/swagger-ui/index.html#/)

