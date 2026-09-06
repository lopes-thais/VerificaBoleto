package com.thais.verificaBoleto.testesUnitarios.modulo10;

import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.parser.ParserLinha;
import com.thais.verificaBoleto.validator.Modulo10;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LinhaInvalidaTest {

    @Test
    void deveRetornarLinhaInvalida() {

        Modulo10 modulo10 = new Modulo10();
        ParserLinha parser = new ParserLinha();

        String linha = "34191738521120955673096238450003715210000213884"; // Linha inválida

        LinhaParseada parseada = parser.extrairCampos(linha);

        boolean resultado = modulo10.validar(parseada);

        assertFalse(resultado);
    }
}
