package main

import (
	"context"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	"grpc-go-client/client"
	"io"
	"log"
	"time"
)

func main() {
	conn, err := grpc.Dial("localhost:9090", grpc.WithTransportCredentials(insecure.NewCredentials()))
	if err != nil {
		log.Fatalf("failed to connect: %v", err)
	}
	defer func(conn *grpc.ClientConn) {
		err := conn.Close()
		if err != nil {
			log.Fatalf("failed to close connection: %v", err)
		}
	}(conn)

	startTimeExecuteHelloService := time.Now()
	executeHelloService(conn, err)
	durationExecuteHelloService := time.Since(startTimeExecuteHelloService)

	startTimeTransactionsService := time.Now()
	executeTransactionsService(conn, err)
	durationTransactionsService := time.Since(startTimeTransactionsService)

	log.Printf("durationExecuteHelloService: %v", durationExecuteHelloService)
	log.Printf("durationTransactionsService: %v", durationTransactionsService)
}

func executeHelloService(conn *grpc.ClientConn, err error) {
	greeterServiceClient := client.NewGreeterServiceClient(conn)
	message, err := greeterServiceClient.SendMessage(context.Background(), &client.MessageRequest{Message: "Hello World!"})
	if err != nil {
		return
	}

	log.Printf("message: %v", message)
}

func executeTransactionsService(conn *grpc.ClientConn, err error) {
	transactionsServiceClient := client.NewGreeterServiceClient(conn)
	transactions, err := transactionsServiceClient.Transactions(context.Background(), &client.TransactionRequest{UserId: "1"})
	if err != nil {
		return
	}

	done := make(chan bool)

	go func() {
		for {
			resp, err := transactions.Recv()
			if err == io.EOF {
				done <- true //close(done)
				return
			}
			if err != nil {
				log.Fatalf("can not receive %v", err)
			}
			log.Printf("Resp received: %s", resp)
		}
	}()

	<-done
}
