package com.example.myspring.grpc.client;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;

public class HelloClient {

    private static String name = "HaoRan";

    public static void main(String[] args) {
        HelloMessage.HelloRequest request = HelloMessage.HelloRequest.newBuilder()
        .setName(name).build();

        // 基于访问地址 创建通道
        Channel channel =  ManagedChannelBuilder.forAddress("localhost", 50001).usePlaintext().build();

        // 利用通道 创建一个桩（Stub）对象
        HelloGrpc.HelloBlockingStub blockingStub = HelloGrpc.newBlockingStub(channel);

        //通过桩对象来调用远程方法
        HelloMessage.HelloResponse helloResponse = blockingStub.sayHello(request);

        System.out.println("[RPC Resp = ]" + helloResponse.getMessage());

    }

}
