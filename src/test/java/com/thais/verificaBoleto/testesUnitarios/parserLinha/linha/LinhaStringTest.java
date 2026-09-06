package com.thais.verificaBoleto.testesUnitarios.parserLinha.linha;

import com.thais.verificaBoleto.parser.ParserLinha;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinhaStringTest {

    @Test
    void deveRejeitarLinhaComCaracteresNaoNumericos(){

        String linha = "3419175850112298325275048415000371100002138842A";
        ParserLinha parser = new ParserLinha();

        assertThrows(
                IllegalArgumentException.class,
                () -> parser.extrairCampos(linha)
        );
    }
}
