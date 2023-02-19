package integration.service;

import com.integration.service.IntegrateRequest;
import com.integration.service.IntegrateResponse;
import com.integration.service.IntegrationServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class IntergrationServiceImpl extends IntegrationServiceGrpc.IntegrationServiceImplBase {

    IntergrationServiceImpl() {
        System.out.println("Hello");
    }

    @Autowired
    private ParserService parserService;

    @Override
    public void integrate(IntegrateRequest request, StreamObserver<IntegrateResponse> responseObserver) {

        IntegrateResponse response = IntegrateResponse.newBuilder()
                .setStatus("greeting")
                .build();

        System.out.println(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
