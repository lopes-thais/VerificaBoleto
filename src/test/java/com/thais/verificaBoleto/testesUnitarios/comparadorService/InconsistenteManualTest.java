package com.thais.verificaBoleto.testesUnitarios.comparadorService;

import com.thais.verificaBoleto.dto.BoletoRequest;
import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.dto.VerificacaoResponse;
import com.thais.verificaBoleto.service.ComparadorService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InconsistenteManualTest {

    @Test
    void deveRetornarDadosInconsistentes(){ // Todos os dados estão inconsistentes

        ComparadorService comparar = new ComparadorService();

        LinhaParseada boleto = new LinhaParseada();
        boleto.setBanco("314");
        boleto.setVencimento(LocalDate.of(2024, 7, 21));
        boleto.setValor(new BigDecimal("34999.90"));

        BoletoRequest linha = new BoletoRequest();
        linha.setBanco("033");
        linha.setDataVencimento(LocalDate.of(2026, 7, 21));
        linha.setValor(new BigDecimal("3499.90"));

        List<VerificacaoResponse> resultado = comparar.compararDadosInformados(boleto, linha);

        assertFalse(resultado.stream().allMatch(VerificacaoResponse::isOk));
    }
}
