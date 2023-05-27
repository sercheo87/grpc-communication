package com.example.springgrpc;

import com.example.springgrpc.model.CardDetails;
import com.github.javafaker.Faker;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringGrpcApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void generateData() {
        String filePath = "./MOCK_DATA.csv";
        int numRows = 100_000;

        Faker faker = new Faker();
        List<String> embossOptions = Arrays.asList("panamericana", "digital", "eth");
        Random random = new Random();

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] header = {"card_number", "expiration_date", "cvv", "card_type", "client_id", "client_name",
                    "client_email", "client_address", "issued_date", "assigned_to", "emboss"};
            writer.writeNext(header);

            for (int i = 0; i < numRows; i++) {
                String cardNumber = faker.business().creditCardNumber();
                String expirationDate = faker.business().creditCardExpiry();
                String cvv = faker.number().digits(3);
                String cardType = faker.business().creditCardType();
                String clientId = String.valueOf(faker.random().nextInt(100000, 999999));
                String clientName = faker.name().fullName();
                String clientEmail = faker.internet().emailAddress();
                String clientAddress = faker.address().fullAddress();
                String issuedDate = faker.date().past(200, TimeUnit.DAYS).toString().substring(0, 10);
                String assignedTo = faker.name().fullName();
                String emboss = embossOptions.get(random.nextInt(embossOptions.size()));

                String[] row = {cardNumber, expirationDate, cvv, cardType, clientId, clientName, clientEmail,
                        clientAddress, issuedDate, assignedTo, emboss};
                writer.writeNext(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readData() {
        String filePath = "./MOCK_DATA.csv";

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            CsvToBean<CardDetails> csvToBean = new CsvToBeanBuilder<CardDetails>(reader)
                    .withType(CardDetails.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreQuotations(true)
                    .build();

            csvToBean.parse().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
