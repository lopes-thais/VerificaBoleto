package com.thais.verificaBoleto.testesUnitarios.comparadorService;

import com.thais.verificaBoleto.agentePdf.dto.AgenteResponse;
import com.thais.verificaBoleto.dto.DadosPdf;
import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.dto.VerificacaoResponse;
import com.thais.verificaBoleto.service.ComparadorService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InconsistentePdfTest {

    @Test
    void deveRetornarInconsistente(){  // Todos os dados estão inconsistentes

        ComparadorService comparar = new ComparadorService();
        LinhaParseada linha = new LinhaParseada();
        DadosPdf pdf = new DadosPdf();

        linha.setValor(new BigDecimal("23345.90"));
        linha.setVencimento(LocalDate.of(2028,6,12));
        linha.setBanco("341");
        linha.setMoeda(8);

        List<BigDecimal> valoresPdf = new ArrayList<>();
        valoresPdf.add(new BigDecimal("2346.98"));
        valoresPdf.add(new BigDecimal("2345.90"));
        valoresPdf.add(new BigDecimal("29876.09"));

        pdf.setValoresEncontrados(valoresPdf);

        List<LocalDate> datasPdf = new ArrayList<>();
        datasPdf.add(LocalDate.of(2025,9,5));
        datasPdf.add(LocalDate.of(2026,8,20));
        datasPdf.add(LocalDate.of(2026,6,12));

        pdf.setDatasEncontradas(datasPdf);
        pdf.setBanco("033");

        AgenteResponse agente = new AgenteResponse(
                LocalDate.of(2026, 7, 28),   // data que o agente "encontrou"
                new BigDecimal("2138.84")   // valor que o agente "encontrou"
        );

        List<VerificacaoResponse> resultado = comparar.compararDadosPdf(linha, pdf, agente);

        assertNotNull(resultado);
        assertEquals(3, resultado.size());

        assertFalse(resultado.stream().allMatch(VerificacaoResponse::isOk));
    }
}
