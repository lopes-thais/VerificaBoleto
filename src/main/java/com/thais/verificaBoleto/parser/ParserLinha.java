package com.thais.verificaBoleto.parser;

import org.springframework.stereotype.Component;
import com.thais.verificaBoleto.dto.LinhaParseada;
import java.math.BigDecimal;

@Component
public class ParserLinha {

    public LinhaParseada extrairCampos(String linha){
 
        linha = linha.replaceAll("\\D", ""); // Remove tudo o que não for dígito
        
        // Verificar se a linha tem o tamanho correto
        if (linha.length() != 47) {
            throw new IllegalArgumentException("Linha digitável inválida.");
        }

        String campo1 = linha.substring(0, 9);
        int dvCampo1 = Character.getNumericValue(linha.charAt(9));

        String campo1Livre = linha.substring(4, 9);

        String campo2 = linha.substring(10, 20);
        int dvCampo2 = Character.getNumericValue(linha.charAt(20));

        String campo3 = linha.substring(21, 31);
        int dvCampo3 = Character.getNumericValue(linha.charAt(31));

        int dvGeral = Character.getNumericValue(linha.charAt(32));
        int moeda = Character.getNumericValue(linha.charAt(3));

        String banco = linha.substring(0, 3);
        String valorString = linha.substring(37, 47);
        BigDecimal valor = new BigDecimal(valorString).movePointLeft(2);

        String fatorString = linha.substring(33, 37);
        int fatorVencimento = Integer.parseInt(fatorString);

        // Monta o objeto com os dados extraídos da linha digitável

        LinhaParseada camposParse = new LinhaParseada();

        camposParse.setDvCampo1(dvCampo1);
        camposParse.setDvCampo2(dvCampo2);
        camposParse.setDvCampo3(dvCampo3);

        camposParse.setCampo1Livre(campo1Livre);
        camposParse.setCampo1(campo1);
        camposParse.setCampo2(campo2);
        camposParse.setCampo3(campo3);

        camposParse.setDvGeral(dvGeral);

        camposParse.setBanco(banco);
        camposParse.setFatorVencimento(fatorVencimento);
        camposParse.setValor(valor);
        camposParse.setMoeda(moeda);

        return camposParse;

    }

}
