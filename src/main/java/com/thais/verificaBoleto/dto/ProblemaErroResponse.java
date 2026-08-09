package com.thais.verificaBoleto.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProblemaErroResponse {

    private int status;
    private String mensagem;
    private LocalDateTime timestamp;
    private List<String> errosDetalhados;

    public ProblemaErroResponse(int status, String mensagem, List<String> errosDetalhados) {
        this.status = status;
        this.mensagem = mensagem;
        this.errosDetalhados = errosDetalhados;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getErrosDetalhados() {
        return errosDetalhados;
    }

    public void setErrosDetalhados(List<String> errosDetalhados) {
        this.errosDetalhados = errosDetalhados;
    }

}
