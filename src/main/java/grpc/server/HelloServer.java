package grpc.server;

import grpc.api.HelloImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class HelloServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server grpcServer = ServerBuilder.forPort(50001)
                .addService(new HelloImpl())
                .build();
        grpcServer.start();
        System.out.println("grpc server ready --");
        // block to exit
        grpcServer.awaitTermination();
    }


}
