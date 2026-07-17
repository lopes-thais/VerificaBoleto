package com.thais.verificaBoleto.util;

import org.springframework.stereotype.Component;
import com.thais.verificaBoleto.dto.LinhaParseada;

@Component
public class ParserLinha {

    public LinhaParseada extrairCampos(String linha){

        // Verificar se a linha tem o tamanho correto

        if (linha == null || linha.length() != 47) {
            throw new IllegalArgumentException("Linha digitável inválida.");
        }

        String campo1 = linha.substring(0, 9);
        int dvCampo1 = Character.getNumericValue(linha.charAt(9));

        String campo2 = linha.substring(11, 20);
        int dvCampo2 = Character.getNumericValue(linha.charAt(20));

        String campo3 = linha.substring(22, 31);
        int dvCampo3 = Character.getNumericValue(linha.charAt(33));

        int dvGeral = Character.getNumericValue(linha.charAt(34));

        // arrays dos campos com caracteres transformados em inteiros
        
        int[] campo1Int = converterParaInteiros(campo1);
        int[] campo2Int = converterParaInteiros(campo2);
        int[] campo3Int = converterParaInteiros(campo3);

        // arrays dos campos com a ordem invertida
    
        int[] campo1Invertido = inverterArray(campo1Int);
        int[] campo2Invertido = inverterArray(campo2Int);
        int[] campo3Invertido = inverterArray(campo3Int);          

        // Monta o objeto com os dados extraídos da linha digitável

        LinhaParseada camposParse = new LinhaParseada();

        camposParse.setCampo1(campo1Invertido);
        camposParse.setCampo2(campo2Invertido);
        camposParse.setCampo3(campo3Invertido);

        camposParse.setDvCampo1(dvCampo1);
        camposParse.setDvCampo2(dvCampo2);
        camposParse.setDvCampo3(dvCampo3);
        camposParse.setDvGeral(dvGeral);

        return camposParse;

    }

    // Método para converter a string dos campos em um array de inteiros

    private int[] converterParaInteiros(String campo) {
            
        int[] numeros = new int[campo.length()];

        for(int i = 0; i < campo.length(); i++){

            numeros[i] = Character.getNumericValue(campo.charAt(i));
        }
        return numeros;
    }

    // Método para inverter a ordem dos elementos do array dos campos

    private int[] inverterArray(int[] numeros){

        int[] invertido = new int[numeros.length];

        for(int i = 0; i < numeros.length; i++){
            
            invertido[i] = numeros[numeros.length - 1 - i];
        }
        return invertido;
    }
}
