gen:
	rm -rf client/*
	protoc --proto_path=../spring-grpc/src/main/proto --go_out=. --go-grpc_out=. ../spring-grpc/src/main/proto/**.proto
