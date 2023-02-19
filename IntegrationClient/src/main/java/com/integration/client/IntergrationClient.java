package com.integration.client;

import com.integration.service.IntegrateRequest;
import com.integration.service.IntegrateResponse;
import com.integration.service.IntegrationServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntergrationClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();

        IntegrationServiceGrpc.IntegrationServiceBlockingStub stub
                = IntegrationServiceGrpc.newBlockingStub(channel);

        IntegrateResponse helloResponse = stub.integrate(IntegrateRequest.newBuilder()
                .setRequestId("Test")
                .build());

        System.out.println(helloResponse);

        channel.shutdown();
    }

}