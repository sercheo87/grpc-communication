package com.example.springgrpc.repository;

import com.example.springgrpc.model.CardDetails;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class CardDetailsService {

    public static List<CardDetails> readCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            CsvToBean<CardDetails> csvToBean = new CsvToBeanBuilder<CardDetails>(reader)
                    .withType(CardDetails.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreQuotations(true)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<CardDetails> readCardDetailsFromFile(String filePath) {
        return readCSV(filePath);
    }
}
