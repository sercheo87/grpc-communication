package com.example.springgrpc.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDetails {
    @CsvBindByName(column = "card_number")
    private String cardNumber;
    @CsvBindByName(column = "expiration_date")
    private String expirationDate;
    @CsvBindByName(column = "cvv")
    private String cvv;
    @CsvBindByName(column = "card_type")
    private String cardType;
    @CsvBindByName(column = "client_id")
    private String clientId;
    @CsvBindByName(column = "client_name")
    private String clientName;
    @CsvBindByName(column = "client_email")
    private String clientEmail;
    @CsvBindByName(column = "client_address")
    private String clientAddress;
    @CsvBindByName(column = "issued_date")
    private String issuedDate;
    @CsvBindByName(column = "assigned_to")
    private String assignedTo;
    @CsvBindByName(column = "emboss")
    private String emboss;
}
