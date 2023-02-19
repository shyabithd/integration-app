package integration.service;

import com.integration.service.IntegrateRequest;
import com.integration.service.IntegrateResponse;
import com.integration.service.IntegrationServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class IntergrationServiceImpl extends IntegrationServiceGrpc.IntegrationServiceImplBase {

    @Autowired
    private ParserService parserService;

    @Override
    public void integrate(IntegrateRequest request, StreamObserver<IntegrateResponse> responseObserver) {

        IntegrateResponse response;

        try {
            parserService.parse();
            response = IntegrateResponse.newBuilder()
                    .setStatus("Completed")
                    .build();
        } catch (Exception e) {
             response = IntegrateResponse.newBuilder()
                    .setStatus("Failed")
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
