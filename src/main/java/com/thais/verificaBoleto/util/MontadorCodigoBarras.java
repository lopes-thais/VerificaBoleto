package com.thais.verificaBoleto.util;

import org.springframework.stereotype.Component;

@Component
public class MontadorCodigoBarras {

    // Transformar a linha digitável em código de barras para utilização no Módulo 11

    public String montarCodigoBarras(String banco, int moeda, String campo1Livre,
                                      String campo2, String campo3, String valorString, String fatorString){

        // Código de barras montado sem o DV geral
        String codigoBarras = banco + moeda + fatorString + valorString + campo1Livre + campo2 + campo3;

        if(codigoBarras.length() != 43){
            throw new IllegalArgumentException("Código de barras inválido");
        }

        return codigoBarras;
    }
}
