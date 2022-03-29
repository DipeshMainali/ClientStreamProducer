package com.example.producer;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import service.ClientStream.Request;
import service.ClientStream.Response;
import service.ClientStreamServiceGrpc;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientStreamProducer {
    private final static Logger logger = Logger.getLogger(ClientStreamProducer.class.getName());
    private final ClientStreamServiceGrpc.ClientStreamServiceStub stub;
    private Response response;

    ClientStreamProducer(Channel channel) {
        this.stub = ClientStreamServiceGrpc.newStub(channel);
        logger.info("stub created");
    }

    public Response calculateResponse(List<Request> expressionRequest) {
        StreamObserver<Request> requestStreamObserver = getRequestObserver();
        logger.info("request stream created");
        try {
            Random random = new Random();

            expressionRequest.forEach(value -> {
                requestStreamObserver.onNext(value);
                try {
                    Thread.sleep(random.nextInt(1000) + 500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } catch (RuntimeException e) {
            logger.info("error : " + e.getMessage());
            requestStreamObserver.onError(e);
            throw e;
        }
        requestStreamObserver.onCompleted();
        return response;
    }

    public StreamObserver<Request> getRequestObserver() {
        return stub.startStream(getServerResponseObserver());
    }

    private StreamObserver<Response> getServerResponseObserver() {
        logger.info("response stream creating");
        return new StreamObserver<Response>() {
            @Override
            public void onNext(Response value) {
                response = value;
                logger.info("response from server : " + value.getTotal());
            }

            @Override
            public void onError(Throwable t) {
                logger.info("error from server : " + t.getMessage());
                logger.log(Level.INFO, t.getMessage());
            }

            @Override
            public void onCompleted() {
                logger.info("complete");
            }
        };
    }
}
