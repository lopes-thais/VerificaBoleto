package com.thais.verificaBoleto.controller;

import com.thais.verificaBoleto.dto.DadosPdf;
import com.thais.verificaBoleto.parser.ExtratorDadosPdf;
import com.thais.verificaBoleto.service.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thais.verificaBoleto.dto.BoletoRequest;
import com.thais.verificaBoleto.dto.BoletoResponse;
import com.thais.verificaBoleto.service.BoletoService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
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
    
    public BoletoResponse verificar(@RequestBody BoletoRequest request) {

        return boletoService.verificar(request);
    }

    @PostMapping("/pdf")
    public BoletoResponse verificarPdf(@RequestParam("application") MultipartFile application)
            throws IOException {

        return boletoService.verificarPdf(application);
        
    }
}
