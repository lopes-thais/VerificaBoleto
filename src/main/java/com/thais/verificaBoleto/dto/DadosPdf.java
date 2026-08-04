package com.thais.verificaBoleto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DadosPdf {

    private String linhaDigitavel;
    private String banco;
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

    public List<LocalDate> getDatasEncontradas() {
        return datasEncontradas;
    }

    public void setDatasEncontradas(List<LocalDate> datasEncontradas) {
        this.datasEncontradas = datasEncontradas;
    }

    public String getBanco(){
        return banco;
    }

    public void setBanco(String banco){
        this.banco = banco;
    }

}
