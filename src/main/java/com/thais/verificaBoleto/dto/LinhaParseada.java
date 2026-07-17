package com.thais.verificaBoleto.dto;

public class LinhaParseada {

    // Classe para armazennar os campos extraídos da linha digitável do boleto e demais dados extraidos

    private int[] campo1;
    private int[] campo2;
    private int[] campo3;

    private int dvCampo1;
    private int dvCampo2;
    private int dvCampo3;

    private int dvGeral;

    // getters e setters

    public int[] getCampo1() {
        return campo1;
    }

    public void setCampo1(int[] campo1) {
        this.campo1 = campo1;
    }

    public int[] getCampo2() {
        return campo2;
    }

    public void setCampo2(int[] campo2) {
        this.campo2 = campo2;
    }

    public int[] getCampo3() {
        return campo3;
    }

    public void setCampo3(int[] campo3) {
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
 
}
