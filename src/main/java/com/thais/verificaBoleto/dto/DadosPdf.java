package com.thais.verificaBoleto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DadosPdf {

    private String linhaDigitavel;
    private List<BigDecimal> valoresEncontrados;
    private List<LocalDate> datasEncontradas;

    public void setLinhaDigitavel(String linhaDigitavel){
        this.linhaDigitavel = linhaDigitavel;
    }

    public String getLinhaDigitavel(){
        return linhaDigitavel;
    }

    public List<BigDecimal> getValoresEncontrados() {
        return valoresEncontrados;
    }

    public void setValoresEncontrados(List<BigDecimal> valoresEncontrados) {
        this.valoresEncontrados = valoresEncontrados;
    }
}
