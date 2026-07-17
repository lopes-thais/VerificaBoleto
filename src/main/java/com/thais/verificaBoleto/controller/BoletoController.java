package com.thais.verificaBoleto.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thais.verificaBoleto.dto.BoletoRequest;
import com.thais.verificaBoleto.dto.BoletoResponse;
import com.thais.verificaBoleto.service.BoletoService;

@RestController
@RequestMapping("/boleto")
public class BoletoController {

    private final BoletoService boletoService;

    public BoletoController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @PostMapping("/verificar")
    
    public BoletoResponse verificar(@RequestBody BoletoRequest request) {

        System.out.println(request.getLinhaDigitavel());
        System.out.println(request.getValor());

        return boletoService.verificar(request);
    }
    
}
