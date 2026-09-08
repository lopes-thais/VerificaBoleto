package com.thais.verificaBoleto.agentePdf;

import com.thais.verificaBoleto.agentePdf.dto.AgenteRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiClient {

    @Value("${gemini.api.url}")
    String apiUrl;

    private final RestClient restClient = RestClient.create();

    public String enviarPrompt(AgenteRequest request){
        String prompt = String.format("""
            Analise os trechos a seguir de um boleto bancário e extraia a data de vencimento e o valor total.
            Envie a data no formato yyyy-mm-dd e o valor com R$ na frente.
            Trechos de Datas: %s
            Trechos de Valores: %s
            """, request.trechosDatas(), request.trechosValores()
        );

        // Constrói payload no formato aceito pela API do Gemini
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                )
        );

        /* O return executa o disparo HTTP informando que o tipo é POST,
            define o endereço de destino (URL da API),
            avisa o servidor do Google que o corpo da requisição é um JSON,
            anexa o payload montado,
            executa a chamada e espera a resposta do servidor do Gemini,
            converte e extrai a resposta em String. */

        return restClient.post()
                .uri(apiUrl)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(String.class);
    }
}
