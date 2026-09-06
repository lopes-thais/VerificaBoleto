package com.thais.verificaBoleto.testesUnitarios.parserLinha.dados;

import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.parser.ParserLinha;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DvGeralValidoTest {

    @Test
    void deveRetornarCampoDvValido() {
        ParserLinha parser = new ParserLinha();
        String linha = "34191758501122298325275048415000371100002138842";
        LinhaParseada resultado = parser.extrairCampos(linha);

        assertEquals(3, resultado.getDvGeral());
    }
}
