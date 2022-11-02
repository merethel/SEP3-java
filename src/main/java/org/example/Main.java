package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import services.EventServiceImpl;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(9090).addService(new EventServiceImpl()).build();
        server.start();
        server.awaitTermination();
    }
}