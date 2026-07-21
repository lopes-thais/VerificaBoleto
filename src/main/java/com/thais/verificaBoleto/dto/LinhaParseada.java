package com.thais.verificaBoleto.dto;

import java.math.BigDecimal;

public class LinhaParseada {

    // Classe para armazennar os campos extraídos da linha digitável do boleto e demais dados extraidos

    private String campo1;
    private String campo2;
    private String campo3;
    private String campo1Livre;

    private int dvCampo1;
    private int dvCampo2;
    private int dvCampo3;

    private String banco;
    private BigDecimal valor;

    private int dvGeral;
    private int fatorVencimento;
    private int moeda;

    private String codigoBarras;
    
    // getters e setters

    public String getCampo1() {
        return campo1;
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public String getCampo2() {
        return campo2;
    }

    public void setCampo2(String campo2) {
        this.campo2 = campo2;
    }

    public String getCampo3() {
        return campo3;
    }

    public void setCampo3(String campo3) {
        this.campo3 = campo3;
    }

    public int getDvCampo1(){
        return dvCampo1;
    }

    public void setDvCampo1(int dvCampo1){
        this.dvCampo1 = dvCampo1;
    }

    public int getDvCampo2(){
        return dvCampo2;
    }

    public void setDvCampo2(int dvCampo2){
        this.dvCampo2 = dvCampo2;
    }

    public int getDvCampo3(){
        return dvCampo3;
    }

    public void setDvCampo3(int dvCampo3){
        this.dvCampo3 = dvCampo3;
    }

    public int getDvGeral() {
        return dvGeral;
    }

    public void setDvGeral(int dvGeral) {
        this.dvGeral = dvGeral;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public int getFatorVencimento() {
        return fatorVencimento;
    }

    public void setFatorVencimento(int fatorVencimento) {
        this.fatorVencimento = fatorVencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setMoeda(int moeda) {
        this.moeda = moeda;
    }

    public int getMoeda(){
        return moeda;
    }

    public void setCodigoBarras(String codigoBarras){
        this.codigoBarras = codigoBarras;
    }

    public String getCodigoBarras(){
        return codigoBarras;
    }

    public void setCampo1Livre(String campo1Livre){
        this.campo1Livre = campo1Livre;
    }

    public String getCampo1Livre(){
        return campo1Livre;
    }
}
