package com.thais.verificaBoleto.testesUnitarios.montadorCodigoBarras;

import com.thais.verificaBoleto.validator.MontadorCodigoBarras;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MontarCodigoLinhaInvalidaTest {

    @Test
    void deveMontarCodigoErrado(){

        MontadorCodigoBarras montador = new MontadorCodigoBarras();

        String codigo = montador.montarCodigoBarras(
                "341",
                7,
                "17585",
                "1120955252",
                "5048415000",
                "0000214584",
                "1521"
        );

        assertNotEquals(
                "3419152100002138841758511209552525048215000",
                codigo
        );

    }
}
