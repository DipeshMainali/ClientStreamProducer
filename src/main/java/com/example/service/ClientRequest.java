package com.example.service;

import com.example.producer.ClientStreamProducerProvider;
import service.ClientStream.*;

import java.util.ArrayList;
import java.util.List;

public class ClientRequest {
    private List<Request> requestExpression;

    public ClientRequest() {
        requestExpression = getDefaultExpression();
    }

    public ClientRequest(List<Request> requestExpression) {
        this.requestExpression = requestExpression;
    }

    public List<Request> getRequestExpression() {
        return requestExpression;
    }

    public void setRequestExpression(List<Request> requestExpression) {
        this.requestExpression = requestExpression;
    }

    public void performCalculation() {
        ClientStreamProducerProvider.getClientStreamProducer().calculateResponse(requestExpression);
    }

    private List<Request> getDefaultExpression() {
        List<Request> requestList = new ArrayList<>();
        requestList.add(Request.newBuilder().setNumber(1).setRule(Rule.MULTIPLY).build());
        requestList.add(Request.newBuilder().setNumber(2).setRule(Rule.ADD).build());
        requestList.add(Request.newBuilder().setNumber(3).setRule(Rule.DIVIDE).build());
        requestList.add(Request.newBuilder().setNumber(4).setRule(Rule.SUBTRACT).build());
        requestList.add(Request.newBuilder().setNumber(5).setRule(Rule.EQUAL).build());
        return requestList;
    }
}
