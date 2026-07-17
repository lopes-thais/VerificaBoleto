package com.thais.verificaBoleto.service;

import org.springframework.stereotype.Service;

import com.thais.verificaBoleto.dto.BoletoRequest;
import com.thais.verificaBoleto.dto.BoletoResponse;

@Service
public class BoletoService {
    
    public BoletoResponse verificar(BoletoRequest request) {

        String linha = request.getLinhaDigitavel(); // Obtém a linha digitável do objeto BoletoRequest
        BoletoResponse response = new BoletoResponse(); // Cria um novo objeto BoletoResponse para armazenar a resposta

        if (linha == null || linha.isBlank()) { // Verifica se a linha digitável é nula ou vazia
            response.setStatus("Fraude");
            return response;

        } else if (linha.length() != 47) { // Verifica se a linha digitável tem exatamente 47 caracteres
            response.setStatus("Fraude");

        } else if (!linha.matches("\\d{47}")) { // Verifica se a linha digitável contém apenas dígitos
            response.setStatus("Fraude");

        } else {
            response.setStatus("Seguro");
        }
        return response;
    }

}
