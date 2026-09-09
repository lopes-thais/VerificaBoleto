# VerificaBoleto — Módulo Agente de IA (Gemini)

Módulo especializado em inteligência artificial do sistema **VerificaBoleto**. Esta branch é responsável pela extração inteligente, análise contextual de dados financeiros e redução do envio de dados sensíveis ao consumir a API do **Google Gemini**.

![Java](https://img.shields.io/badge/Java_21-red?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SPRING_BOOT_3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Google Gemini](https://img.shields.io/badge/Google_Gemini_AI-8E75B2?style=for-the-badge&logo=googlegemini&logoColor=white)
![PDFBox](https://img.shields.io/badge/Apache_PDFBox-CC292B?style=for-the-badge&logo=apache&logoColor=white)

---

## Contexto
Segundo dados do [G1](https://g1.globo.com/politica/noticia/2025/08/14/golpe-pix-boleto-falso-datafolha-fbsp.ghtml), entre 2024 e 2025 cerca de 24 milhões de pessoas foram vítima de golpes envolvendo boletos bancários ou pix.
Nesse mesmo período, o prejuízo agregado gerado por esse tipo de golpe foi de quase R$29 bilhões.
Esses números evidenciam a necessidade urgente de medidas capazes de mitigar a incidência desse tipo de fraude. Entre elas, destacam-se os golpes envolvendo boletos bancários, nos quais o criminoso pode criar um documento visualmente idêntico ao original, mas alterar os dados associados à linha digitável para direcionar o pagamento à sua própria conta. Como a vítima muitas vezes não confere essas informações antes de efetuar o pagamento, o valor pode ser destinado diretamente ao golpista, mesmo que o documento apresentado aparente ser legítimo.

O VerificaBoleto é uma ferramenta de apoio na identificação de possíveis golpes de boletos falsos, identificando divergências entre os dados cadastrados na linha digitável e os que estão no documento do boleto.
## O Desafio Técnico

Uma das maiores dificuldades ao validar boletos em formato PDF é a inconsistência na apresentação dos dados visuais. Diferentes instituições financeiras utilizam nomenclaturas distintas para os mesmos campos (ex: *"Vencimento"*, *"Data de Vencimento"*, *"Pagar até"*, *"Valor do Documento"*, *"Valor Cobrado"*), além de variações no layout do documento.

Criar um *parser* estático via código ou *regex* para cobrir todas as variações existentes exigiria milhares de regras, aumentando a complexidade e a manutenção da aplicação.

## O Agente de IA

Para resolver a variabilidade dos boletos sem inflar a base de código, desenvolvi o `AgenteService`. Ele integra a API do **Google Gemini** para realizar a interpretação semântica do documento e extrair com precisão a **Data de Vencimento** e o **Valor do Boleto**.

### Privacidade e Segurança de Dados (Privacidade por Design)
Enviar o PDF completo ou todo o texto bruto do boleto para APIs externas de IA pode expor dados sensíveis do usuário (como nome, CPF/CNPJ, endereço e código do pagador).

Para contornar essa vulnerabilidade, o sistema implementa a estratégia de **Janela de Contexto**:
1. O texto do PDF é extraído localmente via **Apache PDFBox**.
2. A classe **ExtracaoTrechos** aplica *Regex* para identificar trechos que contêm padrões numéricos e de datas.
3. É criada uma **janela de contexto de 20 caracteres antes e depois** de cada valor/data encontrado.
4. **Apenas essa janela limitada de texto** é enviada no *prompt* para o Gemini, garantindo a privacidade dos dados do usuário.

---

## Configuração do Ambiente
Pré-requisitos:
 * Java 21

 * Maven

 * Chave de API da Google Gemini (GEMINI_API_KEY)

### Variáveis de Ambiente
Para executar esta branch com suporte ao Agente de IA, certifique-se de configurar a sua chave do Gemini no arquivo application.properties ou nas variáveis do sistema:

```bash
gemini.api.url=${GEMINI_API_URL}
```
Sugiro configurar a variável de ambiente utilizando o formato

https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash-lite:generateContent?key=(sua_chave_de_api_aqui)

pois foi o que funcionou para mim. Concatenar no GeminiClient me gerou erros de autorização na hora de enviar a requisição.

Entretanto, você pode alterar o properties para utilizar esse formato abaixo e concatenar criando variáveis no GeminiClient. Lembrando sempre de configurar a chave de API do Gemini nas variáveis de ambiente:

```bash
gemini.api.key=${SUA_CHAVE_AQUI}

gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash-lite:generateContent?key=
```

## Diagrama de Arquitetura e fluxo de execução

<img width="762" height="382" alt="image" src="/docs/FluxoPdf.png" />

## Diagrama de pastas 
```text
VerificaBoleto/
└── src/main/java/com/thais/verificaBoleto/
    │   ├──agentePdf
    │   ├── dto/
    │   │   ├──AgenteRequest
    │   │   └──AgenteResponse
    │   ├── parser/
    │   │   └──ExtracaoTrechos
    │   ├── service/
    │   │   └──AgenteService
    └──GeminiClient 

```

## Desafios Técnicos

- Contornar erro de autorização ao enviar a requisição para a API do Gemini. Tive que criar uma variável de ambiente com o link + chave de API.

## Melhorias Futuras

- Implementação de Testes Unitários e Mocking.