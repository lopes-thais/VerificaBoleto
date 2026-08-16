package com.thais.verificaBoleto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Verifica Boleto API está no ar. Acesse https://verificaboleto-m9zt.onrender.com/swagger-ui/index.html para a documentação.");
    }
}
