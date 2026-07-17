package com.thais.verificaBoleto.util;

import com.thais.verificaBoleto.dto.LinhaParseada;
import org.springframework.stereotype.Component;

@Component
public class Modulo10 {

    public boolean validar(LinhaParseada linhaParseada) {
        
        boolean campo1 = validarCampo(linhaParseada.getCampo1(), linhaParseada.getDvCampo1());
        boolean campo2 = validarCampo(linhaParseada.getCampo2(), linhaParseada.getDvCampo2());
        boolean campo3 = validarCampo(linhaParseada.getCampo3(), linhaParseada.getDvCampo3());

        return campo1 && campo2 && campo3;
    }

    private boolean validarCampo(int[] campo, int dvCampo) {
        
        int soma = 0;
        
        for(int i = 0; i < campo.length; i++) {

            int numero = campo[i];

            if (i % 2 == 0){
                numero *= 2;

                if (numero > 9) {
                    numero -= 9;
                }

                soma += numero;

            } else {
                soma += numero;
            }
        }

        int resto = soma % 10;
        int dvCalculado = 10 - resto;

        if(dvCalculado == 10){
            dvCalculado = 0;
        }

        return dvCalculado == dvCampo; 
 
    }
}
