package com.thais.verificaBoleto.dto;

public class VerificacaoResponse {

    private String campo;
    private String valorInformado;
    private String valorExtraido;
    private boolean ok;
    private String mensagem;

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValorInformado() {
        return valorInformado;
    }

    public void setValorInformado(String valorInformado) {
        this.valorInformado = valorInformado;
    }

    public String getValorExtraido() {
        return valorExtraido;
    }

    public void setValorExtraido(String valorExtraido) {
        this.valorExtraido = valorExtraido;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem(){
        return mensagem;
    }
}
