package com.thais.verificaBoleto.enums;

public enum StatusVerificacao {
    CONSISTENTE("Dados do boleto consistentes com a linha digitável."),
    INCONSISTENTE("Existem divergências entre as informações."),
    INVALIDO("A linha digitável é matematicamente inválida ou está malformatada.");

    private final String descricao;

    StatusVerificacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
