package com.thais.verificaBoleto.testesUnitarios.comparadorService;

import com.thais.verificaBoleto.dto.DadosPdf;
import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.dto.VerificacaoResponse;
import com.thais.verificaBoleto.service.ComparadorService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConsistentePdfTest {

    @Test
    void deveRetornarConsistente(){

        ComparadorService comparar = new ComparadorService();
        LinhaParseada linha = new LinhaParseada();
        DadosPdf pdf = new DadosPdf();

        linha.setValor(new BigDecimal("2345.90"));
        linha.setVencimento(LocalDate.of(2026,6,12));
        linha.setBanco("033");
        linha.setMoeda(9);

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

        List<VerificacaoResponse> resultado = comparar.compararDadosPdf(linha, pdf);

        assertNotNull(resultado);
        assertEquals(3, resultado.size());

        assertTrue(resultado.stream().allMatch(VerificacaoResponse::isOk));
    }
}
