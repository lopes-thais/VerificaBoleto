package com.thais.verificaBoleto.testesUnitarios.parserLinha.dados;

import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.parser.ParserLinha;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataVencimentoValidaTest {

    @Test
    void deveRetornarCampos() {

        ParserLinha parser = new ParserLinha();
        String linha = "34191758501122298325275048415000371100002138842";
        LinhaParseada resultado = parser.extrairCampos(linha);

        assertEquals(LocalDate.of(2041, 11, 15), resultado.getVencimento());
    }
}
