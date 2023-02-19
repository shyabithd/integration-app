package integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, JmsAutoConfiguration.class})
public class IntegrationServer {
    public static void main(String[] args) {
        SpringApplication.run(IntegrationServer.class, args);
    }
}
