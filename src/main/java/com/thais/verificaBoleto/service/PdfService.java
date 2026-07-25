package com.thais.verificaBoleto.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfService {

    // Método para receber e extrair o texto do arquivo PDF do boleto
    public String extrairTexto(MultipartFile application) throws IOException{

        if(application.isEmpty()){
            throw new IllegalArgumentException("Arquivo PDF vazio.");
        }

        if(!"application/pdf".equals(application.getContentType())){
            throw new IllegalArgumentException("O arquivo enviado não é um PDF.");
        }

        try (PDDocument document = Loader.loadPDF(application.getBytes())){ // Recebe o arquivo PDF

            PDFTextStripper stripper = new PDFTextStripper(); // Extrai todo o texto do arquivo

            String texto = stripper.getText(document);

            if(texto == null || texto.isBlank()){
                throw new IllegalArgumentException("Não foi possível extrair o texto do PDF enviado.");
            }

            return texto;
        }
    }
}
