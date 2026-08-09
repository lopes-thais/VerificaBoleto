package com.thais.verificaBoleto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class BoletoRequest {

    @NotBlank(message = "A linha digitável não pode estar em branco.")
    @Pattern(regexp = "\\d{47}", message = "A linha digitável deve possuir 47 digitos numéricos.")
    private String linhaDigitavel;

    @NotNull(message = "O campo data de vencimento não pode estar em branco.")
    private LocalDate dataVencimento;

    @NotNull(message = "O campo valor não pode estar em branco.")
    private BigDecimal valor;

    @NotBlank(message = "O campo banco não pode estar em branco.")
    private String banco;

    public String getLinhaDigitavel() {
        return linhaDigitavel;
    }

    public void setLinhaDigitavel(String linhaDigitavel) {
        this.linhaDigitavel = linhaDigitavel;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }  
}
