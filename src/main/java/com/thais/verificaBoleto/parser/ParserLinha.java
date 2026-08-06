package com.thais.verificaBoleto.parser;

import org.springframework.stereotype.Component;
import com.thais.verificaBoleto.dto.LinhaParseada;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ParserLinha {

    public LinhaParseada extrairCampos(String linha){

        if (linha == null || linha.isBlank()) { // Verifica se a linha digitável é nula ou vazia
            throw new IllegalArgumentException("Linha digitável inválida - Linha dígitavél nula ou vazia.");

        } else if (!linha.matches("\\d{47}")) { // Verifica se a linha digitável contém apenas dígitos
            throw new IllegalArgumentException("Linha digitável inválida - Linha dígitável com caracteres inválidos.");
        }

        // Verificar se a linha tem o tamanho correto
        if (linha.length() != 47) {
            throw new IllegalArgumentException("Linha digitável inválida - Possui menos que 47 dígitos.");

        }

        linha = linha.replaceAll("\\D", ""); // Remove tudo o que não for dígito

        String campo1 = linha.substring(0, 9);
        int dvCampo1 = Character.getNumericValue(linha.charAt(9));

        String campo1Livre = linha.substring(4, 9);

        String campo2 = linha.substring(10, 20);
        int dvCampo2 = Character.getNumericValue(linha.charAt(20));

        String campo3 = linha.substring(21, 31);
        int dvCampo3 = Character.getNumericValue(linha.charAt(31));

        int dvGeral = Character.getNumericValue(linha.charAt(32));
        int moeda = Character.getNumericValue(linha.charAt(3));

        String banco = linha.substring(0, 3);
        String valorString = linha.substring(37, 47);
        BigDecimal valor = new BigDecimal(valorString).movePointLeft(2);

        String fatorString = linha.substring(33, 37);
        int fatorVencimento = Integer.parseInt(fatorString);

        LocalDate vencimento = converterFatorVencimento(fatorVencimento);

        // Monta o objeto com os dados extraídos da linha digitável

        LinhaParseada camposParse = new LinhaParseada();

        camposParse.setDvCampo1(dvCampo1);
        camposParse.setDvCampo2(dvCampo2);
        camposParse.setDvCampo3(dvCampo3);

        camposParse.setCampo1Livre(campo1Livre);
        camposParse.setCampo1(campo1);
        camposParse.setCampo2(campo2);
        camposParse.setCampo3(campo3);

        camposParse.setDvGeral(dvGeral);

        camposParse.setBanco(banco);
        camposParse.setVencimento(vencimento);
        camposParse.setValor(valor);
        camposParse.setMoeda(moeda);
        camposParse.setDataExtraida(fatorString);
        camposParse.setValorString(valorString);

        return camposParse;

    }

    // Método para transformar o fator vencimento em data
    private LocalDate converterFatorVencimento(int fatorVencimento){

        // Data-base do ciclo atual do fator de vencimento:
        // fator 1000 corresponde a 22/02/2025.

        LocalDate dataBase = LocalDate.of(2025, 2, 22);

        int fatorBase = 1000;
        int dias = fatorVencimento - fatorBase;
        LocalDate vencimentoExtraido = dataBase.plusDays(dias);

        return vencimentoExtraido;

    }
}
