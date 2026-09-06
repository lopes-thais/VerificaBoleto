package com.thais.verificaBoleto.testesUnitarios.parserLinha.linha;

import com.thais.verificaBoleto.parser.ParserLinha;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinhaMenosDigitosTest {

    @Test
    void deveRejeitarLinhaComQuantidadeDeDigitosIncorreta() {

        String linha = "34191750000213884";
        ParserLinha parser = new ParserLinha();

        assertThrows(
                IllegalArgumentException.class,
                () -> parser.extrairCampos(linha)
        );
    }
}
