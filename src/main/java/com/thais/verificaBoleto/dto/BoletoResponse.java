package com.thais.verificaBoleto.dto;

import java.util.List;

public class BoletoResponse {
    
    private String status;
    private List<VerificacaoResponse> verificacoes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<VerificacaoResponse> getVerificacoes() {
        return verificacoes;
    }

    public void setVerificacoes(List<VerificacaoResponse> verificacoes) {
        this.verificacoes = verificacoes;
    }

}
