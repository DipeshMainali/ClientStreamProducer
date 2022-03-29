package com.example;

import com.example.service.ClientRequest;
import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.performCalculation();
    }
}
