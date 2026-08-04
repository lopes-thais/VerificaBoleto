package com.thais.verificaBoleto.util;

import com.thais.verificaBoleto.dto.LinhaParseada;
import org.springframework.stereotype.Component;

@Component
public class Modulo11 {

    private final MontadorCodigoBarras montadorCodigoBarras;

    public Modulo11(MontadorCodigoBarras montadorCodigoBarras) {
        this.montadorCodigoBarras = montadorCodigoBarras;
    }

    public boolean validarCodigo(LinhaParseada linhaParseada){

        String codigo = montadorCodigoBarras.montarCodigoBarras(
                linhaParseada.getBanco(),
                linhaParseada.getMoeda(),
                linhaParseada.getCampo1Livre(),
                linhaParseada.getCampo2(),
                linhaParseada.getCampo3(),
                linhaParseada.getValorString(),
                linhaParseada.getDataExtraida()
        );

        int dvInformado = linhaParseada.getDvGeral();

        int dvCalculado = calcularDv(codigo);
        return dvInformado == dvCalculado;
    }

    private int calcularDv(String codigo){

        int peso = 2;
        int soma = 0;

        for(int i = codigo.length() - 1; i >= 0; i--){

            int numero = Character.getNumericValue(codigo.charAt(i));

            int mult = numero * peso;

            peso++;
            soma = soma + mult;

            if(peso > 9){
                peso = 2;
            }
        }

        int resto = soma % 11;
        int dvCalc = 11 - resto;

        if((dvCalc == 0) || (dvCalc == 1) || (dvCalc == 10) || (dvCalc == 11)){
            dvCalc = 1;
        }

        return dvCalc;
    }
}
