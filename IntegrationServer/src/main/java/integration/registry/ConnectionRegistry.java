package integration.registry;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("singleton")
public class ConnectionRegistry {

    private Map<String, ManagedChannel> reg = new HashMap<>();

    @Value("${user.service.endpoint}")
    private String userHost;

    @Value("${user.service.port}")
    private Integer userPort;

    @Value("${product.service.endpoint}")
    private String productHost;

    @Value("${product.service.port}")
    private Integer productPort;

    public void init() {
        reg.put("User", ManagedChannelBuilder.forAddress(userHost, userPort)
                .usePlaintext()
                .build());

        reg.put("Product", ManagedChannelBuilder.forAddress(productHost, productPort)
                .usePlaintext()
                .build());
    }

    public ManagedChannel getChannel(String name) {
        return reg.get(name);
    }
    public void shutdown() {
        reg.forEach( (s, managedChannel) -> managedChannel.shutdown());
    }
}
