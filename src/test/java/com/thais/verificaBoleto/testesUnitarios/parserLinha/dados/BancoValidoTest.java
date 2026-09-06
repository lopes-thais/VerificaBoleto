package com.thais.verificaBoleto.testesUnitarios.parserLinha.dados;

import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.parser.ParserLinha;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BancoValidoTest {
    @Test
    void deveRetornarCampoBancoCorreto() {
        ParserLinha parser = new ParserLinha();
        String linha = "34191758501120955252750484150003715210000213884";
        LinhaParseada resultado = parser.extrairCampos(linha);

        assertEquals("341", resultado.getBanco());
    }
}
