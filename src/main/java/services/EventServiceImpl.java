package services;


import event.GreeterGrpc;
import event.HelloReply;
import event.HelloRequest;
import io.grpc.stub.StreamObserver;

public class EventServiceImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String greeting = String.format("Hello, %s!", request.getName());
        HelloReply response = HelloReply.newBuilder().setMessage(greeting).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
