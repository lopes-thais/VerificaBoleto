package com.thais.verificaBoleto.parser;

import org.springframework.stereotype.Component;
import com.thais.verificaBoleto.dto.DadosPdf;
import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExtratorDadosPdf {

    public DadosPdf extrair (String texto){

        String linhaDigitavel = extrairLinhaDigitavel(texto);

        DadosPdf dados = new DadosPdf();

        dados.setLinhaDigitavel(linhaDigitavel);

        List<LocalDate> datas = extrairDatas(texto);
        List<BigDecimal> valores = extrairValores(texto);

        return dados;
    }

    // Método para extrair a linha digitável do documento de boleto em PDF
    private String extrairLinhaDigitavel(String texto){

        Pattern pattern = Pattern.compile(
                // Procura pelo texto extraído o padrão de linha digitável
                "\\d{5}\\.?\\d{5}\\s*\\d{5}\\.?\\d{6}\\s*\\d{5}\\.?\\d{6}\\s*\\d\\s*\\d{14}"
        );

        Matcher matcher = pattern.matcher(texto);

        if(matcher.find()){

            // Retorna a linha encontrada tirando pontos e espaços
            String linha = matcher.group().replaceAll("\\D", "");

            System.out.println("Linha encontrada: " + linha);
            return linha;
        }

        System.out.println("Nenhuma linha encontrada");

        return null;
    }

    // Método para extrair todas as datas presentes no boleto e guardar em uma lista
    private List<LocalDate> extrairDatas(String texto){

        //Padrão de data
        Pattern pattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
        Matcher matcher = pattern.matcher(texto);

        List<LocalDate> datas = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while(matcher.find()){

            LocalDate data = LocalDate.parse(matcher.group(), formatter);
            datas.add(data);

        }

        return datas;
    }

    // Método para extrair todos os valores presentes no documento do boleto e guardar em uma lista
    private List<BigDecimal> extrairValores(String texto){

        // Padrão de valor para busca no texto extraído
        Pattern pattern = Pattern.compile("\\d{1,3}(?:\\.\\d{3})*,\\d{2}");
        Matcher matcher = pattern.matcher(texto);

        List<BigDecimal> valores = new ArrayList<>();

        while(matcher.find()){

            String valor = matcher.group();
            valor = valor.replace(".", "")
                    .replace(",", ".");

            BigDecimal valorBD = new BigDecimal(valor);

            valores.add(valorBD);
        }

        return valores;
    }

}
