package com.thais.verificaBoleto.testesUnitarios.parserLinha.campos;

import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.parser.ParserLinha;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CampoUmValidoTest {

    @Test
    void deveRetornarCampoeDvCorretos(){

        String linha = "34191758501122983252750484150003711000021388423";
        ParserLinha parser = new ParserLinha();

        LinhaParseada resultado = parser.extrairCampos(linha);

        assertEquals("341917585", resultado.getCampo1());
        assertEquals(0, resultado.getDvCampo1());
    }
}
