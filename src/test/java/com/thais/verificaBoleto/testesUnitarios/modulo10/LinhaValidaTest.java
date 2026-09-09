package com.thais.verificaBoleto.testesUnitarios.modulo10;

import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.parser.ParserLinha;
import com.thais.verificaBoleto.validator.Modulo10;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinhaValidaTest {

    @Test
    void deveRetornarLinhaValida(){

        Modulo10 modulo10 = new Modulo10();
        ParserLinha parser = new ParserLinha();

        /* A classe do módulo 10 valida os 3 primeiros campos da linha e seus respectivos DVs.
            Se você for testar com uma linha válida e quiser verificar se retornaria um False,
            altere qualquer número antes do final do campo 3. Exemplo:

            341917585 0 1122983252 7 8754590873 3 715210000213884
            └───────┘ └ └──────────┘ └ ──────────┘ └ ───────────────
             campo 1  DV   campo 2   DV   campo 3   DV   restante
         */

        String linha = "34191790010104351004791020150008800000000150000";
        LinhaParseada parseada = parser.extrairCampos(linha);

        boolean resultado = modulo10.validar(parseada);

        assertTrue(resultado);
    }
}
