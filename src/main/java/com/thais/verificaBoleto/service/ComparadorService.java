package com.thais.verificaBoleto.service;

import com.thais.verificaBoleto.dto.BoletoRequest;
import com.thais.verificaBoleto.dto.DadosPdf;
import com.thais.verificaBoleto.dto.LinhaParseada;
import com.thais.verificaBoleto.dto.VerificacaoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ComparadorService {

    public List<VerificacaoResponse> compararDadosInformados(LinhaParseada linha, BoletoRequest request){

        VerificacaoResponse banco = new VerificacaoResponse();

        // comparar banco, valores e data
        banco.setCampo("Banco: ");
        banco.setValorInformado(request.getBanco());
        banco.setValorExtraido(linha.getBanco());
        banco.setOk(compararBancos(request.getBanco(), linha.getBanco()));

        return banco;
    }

    public ComparacaoResponse compararDadosPdf(LinhaParseada linha, DadosPdf dadosPdf){

        ComparacaoResponse resultado = new ComparacaoResponse();

        // comparar banco, valores e datas
        resultado.setBancoOk(compararBancos(dadosPdf.getBanco(), linha.getBanco()));
        resultado.setValoresOk(compararValoresExtraidos(dadosPdf.getValoresEncontrados(), linha.getValor()));
        resultado.setDataOk(compararDatasExtraidas(dadosPdf.getDatasEncontradas(), linha.getVencimento()));

        return resultado;
    }

    private boolean compararDatas(LocalDate dataInfo, LocalDate dataExtraida){
        return dataInfo.equals(dataExtraida);
    }

    private boolean compararValores(BigDecimal valorInfo, BigDecimal valorExtraido){
        return valorInfo.compareTo(valorExtraido) == 0;
    }

    private boolean compararBancos(String bancoInfo, String bancoExtraido){
        return bancoInfo.equals(bancoExtraido);
    }

    // Métodos para comparação dos dados em lista extraidos do boleto em PDF
    private boolean compararDatasExtraidas(List<LocalDate> datasLista, LocalDate dataLinha){

        // Para cada data na lista de datas, compare com a data extraida da linha
        for (LocalDate data : datasLista) {

            if (data.equals(dataLinha)) {
                return true;
            }
        }
        return false;
    }

    // Métodos para comparação dos dados em lista extraidos do boleto em PDF
    private boolean compararValoresExtraidos(List<BigDecimal> listaValores, BigDecimal valorLinha){

        // Para cada valor na lista de valores, compare com o valor extraido da linha
        for (BigDecimal valor : listaValores) {

            if (valor.compareTo(valorLinha) == 0) {
                return true;
            }
        }
        return false;
    }
}
