# Generate proto client

## Generation

Example

```bash
python -m grpc_tools.protoc -I<proto_directory> --python_out=<output_directory> --grpc_python_out=<output_directory> <proto_file>.proto
```

Real

```bash
python3 -m grpc_tools.protoc -I './../spring-grpc/src/main/proto' --python_out='./' --grpc_python_out='./' greeter.proto
```
