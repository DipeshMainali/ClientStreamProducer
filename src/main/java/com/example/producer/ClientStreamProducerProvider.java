package com.example.producer;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ClientStreamProducerProvider {
    public static ClientStreamProducer getClientStreamProducer() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext()
                .build();
        return new ClientStreamProducer(channel);
    }
}
