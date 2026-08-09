package com.thais.verificaBoleto.service;

import com.thais.verificaBoleto.dto.*;
import com.thais.verificaBoleto.enums.StatusVerificacao;
import com.thais.verificaBoleto.parser.ExtratorDadosPdf;
import com.thais.verificaBoleto.parser.ParserLinha;
import com.thais.verificaBoleto.validator.Modulo10;
import com.thais.verificaBoleto.validator.Modulo11;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;

@Service
public class BoletoService {

    private static final Logger log = LoggerFactory.getLogger(BoletoService.class);

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
        log.info("Iniciando a verificação do boleto via linha digitável.");

        String linha = request.getLinhaDigitavel(); // Obtém a linha digitável do objeto BoletoRequest
        BoletoResponse response = new BoletoResponse(); // Cria um novo objeto BoletoResponse para armazenar a resposta
       
        LinhaParseada dadosLinha = parserLinha.extrairCampos(linha);

        boolean modulo10Valido = modulo10.validar(dadosLinha);
        boolean modulo11Valido = modulo11.validarCodigo(dadosLinha);

        if (!modulo10Valido || !modulo11Valido) {

            log.warn("Linha digitável com divergência nos Módulos 10/11.");
            response.setStatus(StatusVerificacao.INVALIDO);
            response.setMensagem(StatusVerificacao.INVALIDO.getDescricao());

            return response;
        }

        List<VerificacaoResponse> verificacoes = comparadorService.compararDadosInformados(dadosLinha, request);

        response.setVerificacoes(verificacoes);

        boolean tudoOk = verificacoes.stream().allMatch(VerificacaoResponse::isOk);
        response.setStatus(tudoOk ? StatusVerificacao.CONSISTENTE : StatusVerificacao.INCONSISTENTE);

        StatusVerificacao statusFinal = tudoOk ? StatusVerificacao.CONSISTENTE : StatusVerificacao.INCONSISTENTE;
        response.setStatus(statusFinal);
        response.setMensagem(statusFinal.getDescricao());

        return response;
    }

    public BoletoResponse verificarPdf(MultipartFile arquivo) throws IOException {
        log.info("Iniciando a verificação do boleto via PDF.");

        BoletoResponse response = new BoletoResponse();

        String texto = pdfService.extrairTexto(arquivo);
        DadosPdf dadosPdf = extratorDadosPdf.extrair(texto);
        String linha = dadosPdf.getLinhaDigitavel();

        if(dadosPdf.getLinhaDigitavel() == null){
            throw new IllegalArgumentException("Não foi possível encontrar uma linha digitável.");
        }

        LinhaParseada dadosLinha = parserLinha.extrairCampos(linha);

        boolean modulo10Valido = modulo10.validar(dadosLinha);
        boolean modulo11Valido = modulo11.validarCodigo(dadosLinha);

        if (!modulo10Valido || !modulo11Valido) {

            log.warn("(PDF) Linha digitável com divergência nos Módulos 10/11.");

            response.setStatus(StatusVerificacao.INVALIDO);
            response.setMensagem(StatusVerificacao.INVALIDO.getDescricao());

            return response;
        }

        List<VerificacaoResponse> verificacoes = comparadorService.compararDadosPdf(dadosLinha, dadosPdf);
        response.setVerificacoes(verificacoes);

        boolean tudoOk = verificacoes.stream().allMatch(VerificacaoResponse::isOk);
        response.setStatus(tudoOk ? StatusVerificacao.CONSISTENTE : StatusVerificacao.INCONSISTENTE);

        StatusVerificacao statusFinal = tudoOk ? StatusVerificacao.CONSISTENTE : StatusVerificacao.INCONSISTENTE;
        response.setStatus(statusFinal);
        response.setMensagem(statusFinal.getDescricao());

        return response;
    }

}
