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
        print("Server response:", response.transactionId, response.description, response.amount)


if __name__ == '__main__':
    print_hi('PyCharm')
    execute_grpc_hello()
    execute_grpc_greeter()
