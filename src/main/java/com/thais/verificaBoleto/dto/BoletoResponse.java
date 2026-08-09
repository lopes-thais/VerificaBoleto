package com.thais.verificaBoleto.dto;

import com.thais.verificaBoleto.enums.StatusVerificacao;

import java.util.List;

public class BoletoResponse {

    private List<VerificacaoResponse> verificacoes;
    private StatusVerificacao status;
    private String mensagem;

    public StatusVerificacao getStatus() {
        return status;
    }

    public void setStatus(StatusVerificacao status) {
        this.status = status;
    }

    public List<VerificacaoResponse> getVerificacoes() {
        return verificacoes;
    }

    public void setVerificacoes(List<VerificacaoResponse> verificacoes) {
        this.verificacoes = verificacoes;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
