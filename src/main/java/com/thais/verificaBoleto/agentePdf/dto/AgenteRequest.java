package com.thais.verificaBoleto.agentePdf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Payload com os recortes de contexto de 20 caracteres para análise do Agente de IA")
public record AgenteRequest(
        @Schema(
                description = "Lista de trechos recortados que contêm potenciais datas.",
                example = "[\"Vencimento: 15/10/2026\", \"Data Doc: 01/10/2026\"]"
        )
        List<String> trechosDatas,

        @Schema(
                description = "Lista de trechos recortados que contêm potenciais valores",
                example = "[\"Valor Doc: R$ 150,00\", \"Desconto: R$ 0,00\"]"
        )
        List<String> trechosValores
){}
