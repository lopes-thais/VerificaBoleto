package com.thais.verificaBoleto.agentePdf.parser;

import com.thais.verificaBoleto.agentePdf.service.AgenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExtracaoTrechos {

    public List<String> extrairTrechosContexto(String texto, Pattern pattern, int margem) {

        if(texto == null || texto.isBlank()){
            throw new IllegalArgumentException("O arquivo PDF enviado está em branco ou não possui texto selecionável.");
        }

        List<String> trechos = new ArrayList<>();
        Matcher match = pattern.matcher(texto);

        while (match.find()) {
            int inicio = Math.max(0, match.start() - margem);
            int fim = Math.min(texto.length(), match.end() + margem);

            String trechoComContexto = texto.substring(inicio, fim).replaceAll("\\s+", " ").trim();
            trechos.add(trechoComContexto);
        }
        return trechos;
    }
}
