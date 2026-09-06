package com.thais.verificaBoleto.testesUnitarios.parserLinha.campos;

import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.parser.ParserLinha;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CampoTresValidoTest {
    @Test
    void deveRetornarCampoeDvCorretos(){

        String linha = "34191758501122983252750484150003711000021388423";
        ParserLinha parser = new ParserLinha();

        LinhaParseada resultado = parser.extrairCampos(linha);

        assertEquals("5048415000", resultado.getCampo3());
        assertEquals(3, resultado.getDvCampo3());
    }
}
