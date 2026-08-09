package com.thais.verificaBoleto.service;

import com.thais.verificaBoleto.dto.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComparadorService {

    public List<VerificacaoResponse> compararDadosInformados(LinhaParseada linha, BoletoRequest request){

       List<VerificacaoResponse> verificacoes = new ArrayList<>();

       VerificacaoResponse banco = (criarVerificacao("Banco", request.getBanco(), linha.getBanco(),
                                        compararBancos(request.getBanco(), linha.getBanco())));

       if(!compararBancos(request.getBanco(), linha.getBanco())){
           banco.setMensagem("Banco informado diferente do banco na linha digitável.");
       }

       verificacoes.add(banco);

       VerificacaoResponse valor = (criarVerificacao("Valor", request.getValor().toString(), linha.getValor().toString(),
                compararValores(request.getValor(), linha.getValor())));

       BigDecimal diferenca = request.getValor().subtract(linha.getValor()).abs();
       String diferencaFormatada = diferenca.setScale(2, RoundingMode.HALF_UP).toString();

       if(diferenca.compareTo(BigDecimal.ZERO) != 0){
           valor.setMensagem(
                   "Foi encontrada uma divergência de R$" + diferencaFormatada + " no valor do boleto."
           );
       }

       verificacoes.add(valor);

       VerificacaoResponse vencimento = criarVerificacao("Vencimento", request.getDataVencimento().toString(), linha.getVencimento().toString(),
                compararDatas(request.getDataVencimento(), linha.getVencimento()));

       if (verificarToleranciaData(linha.getVencimento(), request.getDataVencimento())) {
            vencimento.setMensagem(
                    "A data difere em 1 dia, mas foi considerada válida."
            );

       } else if(!verificarToleranciaData(linha.getVencimento(), request.getDataVencimento())){

           vencimento.setMensagem("O vencimento informado é divergente do cadastrado na linha digitável.");

       }

       verificacoes.add(vencimento);
       return verificacoes;
    }

    public List<VerificacaoResponse> compararDadosPdf(LinhaParseada linha, DadosPdf dadosPdf){

        List<VerificacaoResponse> verificacoes = new ArrayList<>();
        BoletoResponse mensagem = new BoletoResponse();

        BigDecimal valorEncontrado = encontrarValor(
                dadosPdf.getValoresEncontrados(),
                linha.getValor()
        );

        LocalDate dataEncontrada = encontrarData(
                dadosPdf.getDatasEncontradas(), linha.getVencimento()
        );

        verificacoes.add(criarVerificacao("Banco", linha.getBanco(), dadosPdf.getBanco(),
                compararBancos(linha.getBanco(), dadosPdf.getBanco())));

        verificacoes.add(criarVerificacao(
                "Valor",
                linha.getValor().toString(),
                valorEncontrado != null ? valorEncontrado.toString() : "Não encontrado",
                valorEncontrado != null
        ));

        VerificacaoResponse vencimento = criarVerificacao(
                "Vencimento",
                linha.getVencimento().toString(),
                dataEncontrada != null ? dataEncontrada.toString() : "Não encontrado",
                dataEncontrada != null
        );

        if (verificarToleranciaData(linha.getVencimento(), dataEncontrada)) {
            vencimento.setMensagem(
                    "A data difere em 1 dia, mas foi considerada válida."
            );
        }

        verificacoes.add(vencimento);

        return verificacoes;
    }

    // Métodos para comparação dos dados
    private boolean compararDatas(LocalDate dataInfo, LocalDate dataExtraida){

        if (dataInfo == null || dataExtraida == null) {
            return false;
        }

        long diferenca = Math.abs(
                ChronoUnit.DAYS.between(dataInfo, dataExtraida)
        );

        return diferenca <= 1;
    }

    private boolean compararValores(BigDecimal valorInfo, BigDecimal valorExtraido){
        return valorInfo.compareTo(valorExtraido) == 0;
    }

    private boolean compararBancos(String bancoInfo, String bancoExtraido){

        return bancoInfo.equals(bancoExtraido);
    }

    // Métodos para procurar na lista a data correspondente à linha digitável
    private LocalDate encontrarData(List<LocalDate> datasLista, LocalDate dataLinha){

        // Para cada data na lista de datas, compare com a data extraida da linha
        for (LocalDate data : datasLista) {

            long diferenca = Math.abs(
                    ChronoUnit.DAYS.between(data, dataLinha)
            );

            if (diferenca <= 1) {
                return data;
            }
        }
        return null;

    }

    public boolean verificarToleranciaData(LocalDate dataLinha, LocalDate dataEncontrada) {

        if (dataLinha == null || dataEncontrada == null) {
            return false;
        }

        long diferenca = Math.abs(
                ChronoUnit.DAYS.between(dataLinha, dataEncontrada)
        );

        return diferenca == 1;
    }

    // Métodos para comparar os valores em lista extraidos e encontrar o da linah no boleto em PDF

    private BigDecimal encontrarValor(List<BigDecimal> listaValores, BigDecimal valorLinha) {

        for (BigDecimal valor : listaValores) {
            if (valor.compareTo(valorLinha) == 0) {
                return valor;
            }
        }

        return null;
    }

    private VerificacaoResponse criarVerificacao(String campo, String valorInformado,
                                                 String valorExtraido, boolean ok){

        VerificacaoResponse verificacao = new VerificacaoResponse();

        verificacao.setCampo(campo);
        verificacao.setValorInformado(valorInformado);
        verificacao.setValorExtraido(valorExtraido);
        verificacao.setOk(ok);

        return verificacao;
    }
}
