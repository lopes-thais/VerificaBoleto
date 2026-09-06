package com.thais.verificaBoleto.testesUnitarios.modulo11;

import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.parser.ParserLinha;
import com.thais.verificaBoleto.validator.Modulo11;
import com.thais.verificaBoleto.validator.MontadorCodigoBarras;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinhaValidaTest {

    @Test
    void deveRetornarLinhaValida(){

        MontadorCodigoBarras montador = new MontadorCodigoBarras();
        Modulo11 modulo11 = new Modulo11(montador);
        ParserLinha parser = new ParserLinha();

        String linha = "34191758501122983252750484150003711000021388423";
        LinhaParseada linhaParseada = parser.extrairCampos(linha);

        boolean resultado = modulo11.validarCodigo(linhaParseada);

        assertTrue(resultado);
    }
}
