package com.thais.verificaBoleto.agentePdf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO que armazena a data de venciemnto e valor do boleto identificados pelo agente de IA.")
public record AgenteResponse(

        @Schema(description = "Data de vencimento identificada", example = "2026-10-15")
        LocalDate data,

        @Schema(description = "Valor total do boleto identificado", example = "150.00")
        BigDecimal valor
){}
