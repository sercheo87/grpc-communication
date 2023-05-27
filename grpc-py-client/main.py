import time

import grpc

import greeter_pb2
import greeter_pb2_grpc


def print_hi(name):
    print(f'Hi, {name}')  # Press ⌘F8 to toggle the breakpoint.


def execute_grpc_hello():
    channel = grpc.insecure_channel('localhost:9090')
    stub = greeter_pb2_grpc.GreeterServiceStub(channel)

    # Create a request message
    request = greeter_pb2.MessageRequest()
    request.message = "Hello, gRPC server!"

    # request = greeter_pb2.DESCRIPTOR(name='John')
    response = stub.SendMessage(request)
    print("Server response:", response.reply)


def execute_grpc_greeter():
    channel = grpc.insecure_channel('localhost:9090')
    stub = greeter_pb2_grpc.GreeterServiceStub(channel)

    # Create a request message
    request = greeter_pb2.TransactionRequest()
    request.userId = "1234"

    # Consume server streaming RPC method
    for response in stub.Transactions(request):
        print("Server response:", response)


if __name__ == '__main__':
    print_hi('PyCharm')

    start = time.time()
    execute_grpc_hello()
    duration_execute_grpc_hello = time.time() - start

    start = time.time()
    execute_grpc_greeter()
    duration_execute_grpc_greeter = time.time() - start

    print("Duration execute_grpc_hello:", duration_execute_grpc_hello)
    print("Duration execute_grpc_greeter:", duration_execute_grpc_greeter)
