package com.thais.verificaBoleto.agentePdf.service;

import com.thais.verificaBoleto.agentePdf.GeminiClient;
import com.thais.verificaBoleto.agentePdf.dto.AgenteRequest;
import com.thais.verificaBoleto.agentePdf.dto.AgenteResponse;
import com.thais.verificaBoleto.agentePdf.parser.ExtracaoTrechos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AgenteService {

    private static final Logger log = LoggerFactory.getLogger(AgenteService.class);
    private final ExtracaoTrechos extratorTrechos;
    private final GeminiClient agente;

    private static final Pattern PATTERN_DATA = Pattern.compile("\\d{2}[/. ]\\d{2}[/. ]\\d{4}");
    private static final Pattern PATTERN_VALOR = Pattern.compile("(?:R\\$\\s*)?\\d{1,3}(?:\\.\\d{3})*,\\d{2}");
    private static final int MARGEM_CONTEXTO = 15;

    public AgenteService(ExtracaoTrechos extratorTrechos, GeminiClient agente) {
        this.extratorTrechos = extratorTrechos;
        this.agente = agente;
    }

    /* Recebe o texto bruto do PDF, extrai os trechos onde contém datas ou valores monetários
        e envia para análise do Agente de IA.
     */
    public AgenteResponse analisarTrechos(String texto){

        List<String> datas = extratorTrechos.extrairTrechosContexto(texto, PATTERN_DATA, MARGEM_CONTEXTO);
        List<String> valores = extratorTrechos.extrairTrechosContexto(texto, PATTERN_VALOR, MARGEM_CONTEXTO);

        AgenteRequest request = new AgenteRequest(datas, valores);

        try {
            log.info("Enviando requisição para a API do Gemini...");
            String respostaAgente = agente.enviarPrompt(request);

            log.info(respostaAgente);
            return converterRespostaAgente(respostaAgente);

        }catch(Exception e){
            log.error("Erro ao comunicar com o Agente Gemini: {}", e.getMessage());
            return fallbackAgenteIndisponivel();
        }
    }

    // Caso o Agente esteja indisponível, o sistema roda verifica data e valor via regex
    private AgenteResponse fallbackAgenteIndisponivel() {

        return new AgenteResponse(null, null);
    }

    // Extrai a data e o valor, converte para LocalDate e BigDecimal e armazena no DTO
    private AgenteResponse converterRespostaAgente(String respostaAgente){

        Pattern patternData = Pattern.compile("\\b(\\d{4}-\\d{2}-\\d{2})\\b");
        Matcher matchData = patternData.matcher(respostaAgente);

        LocalDate vencimento = null;
        if(matchData.find()){
            vencimento = LocalDate.parse(matchData.group(1));
        }

        Pattern patternValor = Pattern.compile("R\\$\\s*(\\d{1,3}(?:\\.\\d{3})*,\\d{2})");
        Matcher matchValor = patternValor.matcher(respostaAgente);

        BigDecimal valor = null;
        if(matchValor.find()){
            String valorLimpo = matchValor.group(1).replace(".", "").replace(",", ".");
            valor = new BigDecimal(valorLimpo);
        }

        return new AgenteResponse(vencimento, valor);
    }
}
