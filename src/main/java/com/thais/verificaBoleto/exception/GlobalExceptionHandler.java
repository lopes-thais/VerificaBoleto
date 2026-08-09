package com.thais.verificaBoleto.exception;

import com.thais.verificaBoleto.dto.ProblemaErroResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // logger SLF4J corporativo
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Captura IllegalArgumentException (ex: linha inválida ou malformatada)

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemaErroResponse> tratarErroValidacao (IllegalArgumentException e){
        log.warn("Tentativa de verificação com dados inválidos: {}", e.getMessage());

        ProblemaErroResponse erro = new ProblemaErroResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                List.of(e.getMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

    }

    // Captura erros de validação das anotações @Valid no DTO

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemaErroResponse> tratarErrosDTO (MethodArgumentNotValidException e){
        List<String> erros = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        log.warn("Requisição mal formatada recebida: {}" , erros);

        ProblemaErroResponse erro = new ProblemaErroResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação nos campos informados.",
                erros
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Captura erros inesperados do servidor para evitar stacktrace para o usuário

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemaErroResponse> tratarErroInesperado(Exception e){
        log.error("Erro interno não esperado: " + e);

        ProblemaErroResponse erro = new ProblemaErroResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro interno ao processar a verificação do boleto.",
                List.of(e.getMessage())
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    // Captura erros de sintaxe no JSON ou de formato de data ou número inválido
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemaErroResponse> tratarErroLeituraJson(HttpMessageNotReadableException e){
        log.warn("Falha ao ler o JSON enviado - erro de sintaxe ou formatação: {}", e.getMessage());

        var erro = new ProblemaErroResponse(
                HttpStatus.BAD_REQUEST.value(),
                "O campo da requisição contém um JSON mal formatado ou campos com formatos inválidos (ex. datas)",
                List.of("Verifique se as datas estão no formato correto (YYYY/MM/DD) e a sintaxe do json.")
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
