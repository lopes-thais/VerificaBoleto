package com.thais.verificaBoleto.testesUnitarios.montadorCodigoBarras;

import com.thais.verificaBoleto.validator.MontadorCodigoBarras;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MontarCodigoLinhaValidaTest {

    @Test
    void deveMontarCodigoCorretamente(){
        MontadorCodigoBarras montador = new MontadorCodigoBarras();

        String codigo = montador.montarCodigoBarras(
                "341",
                9,
                "17585",
                "1120955252",
                "5048415000",
                "0000213884",
                "1521"
        );

        assertEquals(
                "3419152100002138841758511209552525048415000",
                codigo
        );
    }
}