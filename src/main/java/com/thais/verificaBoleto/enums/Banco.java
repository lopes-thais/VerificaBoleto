package com.thais.verificaBoleto.enums;

public enum Banco {
    BANCO_DO_BRASIL("001", "Banco do Brasil"),
    BANCO_DA_AMAZONIA("003", "Banco da Amazônia"),
    BANCO_DO_NORDESTE("004", "Banco do Nordeste"),
    SANTANDER("033", "Santander"),
    BANRISUL("041", "Banrisul"),
    BRB("070", "BRB"),
    INTER("077", "Banco Inter"),
    CAIXA("104", "Caixa Econômica Federal"),
    BRADESCO("237", "Bradesco"),
    NUBANK("260", "Nubank"),
    C6_BANK("336", "C6 Bank"),
    ITAU("341", "Itaú"),
    SAFRA("422", "Safra"),
    SICOOB("756", "Sicoob"),
    SICREDI("748", "Sicredi");

    private final String codigo;
    private final String nome;

    Banco(String codigo, String nome){
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getCodigoNome() {
        return codigo + " - " + nome;
    }

    public String getNome() {
        return nome;
    }

    public static Banco encontrarPorCodigo(String codigo){
        for (Banco banco : values()) {

            if (banco.codigo.equals(codigo)) {
                return banco;
            }
        }

        return null;
    }
}


