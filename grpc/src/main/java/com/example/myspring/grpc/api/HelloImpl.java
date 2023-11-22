package com.example.myspring.grpc.api;

import io.grpc.stub.StreamObserver;

public class HelloImpl extends HelloGrpc.HelloImplBase {

    @Override
    public void sayHello(HelloMessage.HelloRequest request, StreamObserver<HelloMessage.HelloResponse> responseObserver) {
        HelloMessage.HelloResponse reply = HelloMessage.HelloResponse.newBuilder().setMessage("say Hello to : " + request.getName()).build();

        // 调用onNext()方法来通知gRPC框架把reply 从server端 发送回 client端
        responseObserver.onNext(reply);
        // 表示完成调用
        responseObserver.onCompleted();

    }
}
