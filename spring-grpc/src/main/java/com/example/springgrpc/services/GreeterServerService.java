package com.example.springgrpc.services;

import com.example.grpc.ExampleProto;
import com.example.grpc.GreeterServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreeterServerService extends GreeterServiceGrpc.GreeterServiceImplBase {

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
        // Simulating multiple transactions for demonstration purposes
        for (int i = 1; i <= 5; i++) {
            String transactionId = "TXN00" + i;
            String description = "Transaction " + i;
            float amount = (float) (Math.random() * 1000);

            ExampleProto.TransactionResponse response = ExampleProto.TransactionResponse.newBuilder()
                    .setTransactionId(transactionId)
                    .setDescription(description)
                    .setAmount(amount)
                    .build();

            responseObserver.onNext(response);

            try {
                // Adding a delay of 1 second between each transaction
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        responseObserver.onCompleted();
    }

}
