package com.thais.verificaBoleto.testesUnitarios.modulo11;

import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.parser.ParserLinha;
import com.thais.verificaBoleto.validator.Modulo11;
import com.thais.verificaBoleto.validator.MontadorCodigoBarras;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class LinhaInvalidaTest {

    @Test
    void deveRetornarLinhaInvalida(){

        MontadorCodigoBarras montador = new MontadorCodigoBarras();
        Modulo11 modulo11 = new Modulo11(montador);
        ParserLinha parser = new ParserLinha();

        String linha = "34191758501122983252750484215000371104002138842"; // Linha inválida
        LinhaParseada linhaParseada = parser.extrairCampos(linha);

        boolean resultado = modulo11.validarCodigo(linhaParseada);

        assertFalse(resultado);
    }
}
