package com.thais.verificaBoleto.service;

import com.thais.verificaBoleto.dto.*;
import com.thais.verificaBoleto.parser.ExtratorDadosPdf;
import com.thais.verificaBoleto.parser.ParserLinha;
import com.thais.verificaBoleto.validator.Modulo10;
import com.thais.verificaBoleto.validator.Modulo11;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BoletoService {

    private final PdfService pdfService;
    private final ExtratorDadosPdf extratorDadosPdf;
    private final ParserLinha parserLinha;
    private final Modulo10 modulo10;
    private final Modulo11 modulo11;
    private final ComparadorService comparadorService;

    public BoletoService(PdfService pdfService, ExtratorDadosPdf extratorDadosPdf,
                         ParserLinha parserLinha, Modulo10 modulo10, Modulo11 modulo11,
                         ComparadorService comparadorService) {

        this.pdfService = pdfService;
        this.extratorDadosPdf = extratorDadosPdf;
        this.parserLinha = parserLinha;
        this.modulo10 = modulo10;
        this.modulo11 = modulo11;
        this.comparadorService = comparadorService;

    }

    public BoletoResponse verificar(BoletoRequest request) {

        String linha = request.getLinhaDigitavel(); // Obtém a linha digitável do objeto BoletoRequest
        BoletoResponse response = new BoletoResponse(); // Cria um novo objeto BoletoResponse para armazenar a resposta

        if (linha == null || linha.isBlank()) {
            response.setStatus("Linha digitável em branco.");
            return response;
        }

        try {
            LinhaParseada dadosLinha = parserLinha.extrairCampos(linha);

            boolean modulo10Valido = modulo10.validar(dadosLinha);
            boolean modulo11Valido = modulo11.validarCodigo(dadosLinha);

            if (!modulo10Valido || !modulo11Valido) {
                response.setStatus("Linha digitável matematicamente inválida!");

                return response;
            }

            List<VerificacaoResponse> verificacoes =
                    comparadorService.compararDadosInformados(dadosLinha, request);

            response.setVerificacoes(verificacoes);

            boolean tudoOk = verificacoes.stream()
                    .allMatch(VerificacaoResponse::isOk);

            response.setStatus(tudoOk ? "Dados consistentes" : "Suspeito");

        }catch (IllegalArgumentException e) {
                e.printStackTrace();
                response.setStatus(e.getMessage());
                return response;
        }

        return response;
    }

    public BoletoResponse verificarPdf(MultipartFile arquivo) throws IOException {

        BoletoResponse response = new BoletoResponse();

        String texto = pdfService.extrairTexto(arquivo);
        DadosPdf dadosPdf = extratorDadosPdf.extrair(texto);
        String linha = dadosPdf.getLinhaDigitavel();

        LinhaParseada dadosLinha = parserLinha.extrairCampos(linha);

        boolean modulo10Valido = modulo10.validar(dadosLinha);
        boolean modulo11Valido = modulo11.validarCodigo(dadosLinha);

        try {
            if (!modulo10Valido || !modulo11Valido) {
                response.setStatus("Linha digitável matematicamente inválida!");
                return response;
            }

            response.setVerificacoes(
                    comparadorService.compararDadosPdf(dadosLinha, dadosPdf)
            );

            List<VerificacaoResponse> verificacoes =
                    comparadorService.compararDadosPdf(dadosLinha, dadosPdf);

            boolean tudoOk = verificacoes.stream()
                    .allMatch(VerificacaoResponse::isOk);

            response.setStatus(tudoOk ? "Dados consistentes" : "Suspeito");

        }catch(IllegalArgumentException e) {
            e.printStackTrace();
            response.setStatus(e.getMessage());
            return response;
        }

        return response;
    }

}
