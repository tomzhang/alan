package cn.com.sina.alan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by whf on 7/22/16.
 */
@SpringBootApplication
@EnableEurekaServer
public class AlanRegistryServer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AlanRegistryServer.class);
        app.setAddCommandLineProperties(true);
        app.run(args);
        //SpringApplication.run(AlanRegistryServer.class);
    }
}
