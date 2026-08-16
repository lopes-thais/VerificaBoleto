package com.thais.verificaBoleto.controller;

import com.thais.verificaBoleto.dto.DadosPdf;
import com.thais.verificaBoleto.enums.StatusVerificacao;
import com.thais.verificaBoleto.parser.ExtratorDadosPdf;
import com.thais.verificaBoleto.service.PdfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thais.verificaBoleto.dto.BoletoRequest;
import com.thais.verificaBoleto.dto.BoletoResponse;
import com.thais.verificaBoleto.service.BoletoService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/boleto")
@Tag(name = "Boleto", description = "Endpoints para verificação de boletos via PDF, inserindo os dados manualmente, e extração de dados do documento PDF" )
public class BoletoController {

    private final BoletoService boletoService;
    private final PdfService pdfService;
    private final ExtratorDadosPdf extratorDadosPdf;

    public BoletoController(BoletoService boletoService, PdfService pdfService, ExtratorDadosPdf extratorDadosPdf) {
        this.boletoService = boletoService;
        this.pdfService = pdfService;
        this.extratorDadosPdf = extratorDadosPdf;
    }

    @Operation(
            summary = "Verificar boleto via dados manuais",
            description = "Valida a linha digitável matematicamente (módulos 10 e 11) e compara com os dados informados pelo usuário."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verificação concluída (consistente ou inconsistente)"),
            @ApiResponse(responseCode = "400", description = "Linha digitável inválida ou campos malformatados")
    })
    @PostMapping("/verificar")
    public ResponseEntity<BoletoResponse> verificar(@Valid @RequestBody BoletoRequest request) {
        BoletoResponse response = boletoService.verificar(request);

        if (response.getStatus() == StatusVerificacao.INVALIDO) {
            return ResponseEntity.badRequest().body(response); // status 400
        }

        return ResponseEntity.ok(response); // status 200
    }

    @Operation(
            summary = "Verificar boleto via documento PDF",
            description = "Extrai os dados do documento enviado, valida a linha digitável, extrai dados da linha e compara com os do PDF"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verificação concluída (consistente ou inconsistente)"),
            @ApiResponse(responseCode = "400", description = "PDF inválido ou linha digitável não encontrada")
    })
    @PostMapping(value = "/pdf" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BoletoResponse> verificarPdf(@RequestParam("arquivo") MultipartFile application)
            throws IOException {

        BoletoResponse response = boletoService.verificarPdf(application);

        if (response.getStatus() == StatusVerificacao.INVALIDO) {
            return ResponseEntity.badRequest().body(response); // status 400
        }

        return ResponseEntity.ok(response);
        
    }

    @PostMapping(value = "/pdf/extrair", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> extrairDados(@RequestParam("arquivo") MultipartFile arquivo) throws IOException {
        String texto = pdfService.extrairTexto(arquivo);
        DadosPdf dados = extratorDadosPdf.extrair(texto);

        if (dados.getLinhaDigitavel() == null) {
            throw new IllegalArgumentException("Não foi possível encontrar uma linha digitável no PDF.");
        }

        return ResponseEntity.ok(Map.of(
                "linhaDigitavel", dados.getLinhaDigitavel(),
                "valoresEncontrados", dados.getValoresEncontrados().size(),
                "datasEncontradas", dados.getDatasEncontradas().size()
        ));
    }


    @GetMapping("/")
    public ResponseEntity<String> home() {

        return ResponseEntity.ok("Verifica Boleto API está no ar. Acesse https://verificaboleto-m9zt.onrender.com/swagger-ui/index.html para a documentação.");

    }
}
