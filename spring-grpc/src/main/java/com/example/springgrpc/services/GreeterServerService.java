package com.example.springgrpc.services;

import com.example.grpc.ExampleProto;
import com.example.grpc.GreeterServiceGrpc;
import com.example.springgrpc.model.CardDetails;
import com.example.springgrpc.repository.CardDetailsService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@AllArgsConstructor
@Slf4j
public class GreeterServerService extends GreeterServiceGrpc.GreeterServiceImplBase {

    private final CardDetailsService cardDetailsService;

    @Override
    public void sendMessage(ExampleProto.MessageRequest request, StreamObserver<ExampleProto.MessageResponse> responseObserver) {
        String greeting = "Hello, " + request.getMessage() + "!";
        ExampleProto.MessageResponse response = ExampleProto.MessageResponse.newBuilder()
                .setReply(greeting)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void transactions(ExampleProto.TransactionRequest request, StreamObserver<ExampleProto.TransactionResponse> responseObserver) {

        List<CardDetails> cardDetails1 = cardDetailsService.readCardDetailsFromFile("./MOCK_DATA.csv");
        log.info("cardDetails1: {}", cardDetails1.size());

        cardDetails1.forEach(cardDetails -> {
            ExampleProto.TransactionResponse cardDetailsResponse = ExampleProto.TransactionResponse.newBuilder()
                    .setCardNumber(cardDetails.getCardNumber())
                    .setExpirationDate(cardDetails.getExpirationDate())
                    .setCvv(cardDetails.getCvv())
                    .setCardType(cardDetails.getCardType())
                    .setClientId(cardDetails.getClientId())
                    .setClientName(cardDetails.getClientName())
                    .setClientEmail(cardDetails.getClientEmail())
                    .setClientAddress(cardDetails.getClientAddress())
                    .setIssuedDate(cardDetails.getIssuedDate())
                    .setAssignedTo(cardDetails.getAssignedTo())
                    .setEmboss(cardDetails.getEmboss())
                    .build();

            responseObserver.onNext(cardDetailsResponse);
        });

        responseObserver.onCompleted();
    }

}
