package com.thais.verificaBoleto.controller;

import com.thais.verificaBoleto.dto.DadosPdf;
import com.thais.verificaBoleto.enums.StatusVerificacao;
import com.thais.verificaBoleto.parser.ExtratorDadosPdf;
import com.thais.verificaBoleto.service.PdfService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thais.verificaBoleto.dto.BoletoRequest;
import com.thais.verificaBoleto.dto.BoletoResponse;
import com.thais.verificaBoleto.service.BoletoService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/boleto")
public class BoletoController {

    private final BoletoService boletoService;
    private final PdfService pdfService;
    private final ExtratorDadosPdf extratorDadosPdf;

    public BoletoController(BoletoService boletoService, PdfService pdfService, ExtratorDadosPdf extratorDadosPdf) {
        this.boletoService = boletoService;
        this.pdfService = pdfService;
        this.extratorDadosPdf = extratorDadosPdf;
    }

    @PostMapping("/verificar")
    public ResponseEntity<BoletoResponse> verificar(@Valid @RequestBody BoletoRequest request) {
        BoletoResponse response = boletoService.verificar(request);

        if (response.getStatus() == StatusVerificacao.INVALIDO) {
            return ResponseEntity.badRequest().body(response); // status 400
        }

        return ResponseEntity.ok(response); // status 200
    }

    @PostMapping("/pdf")
    public ResponseEntity<BoletoResponse> verificarPdf(@RequestParam("application") MultipartFile application)
            throws IOException {

        BoletoResponse response = boletoService.verificarPdf(application);

        if (response.getStatus() == StatusVerificacao.INVALIDO) {
            return ResponseEntity.badRequest().body(response); // status 400
        }

        return ResponseEntity.ok(response);
        
    }

    @PostMapping("/pdf/extrair")
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
}
