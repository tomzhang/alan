package cn.com.sina.alan.config;

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
        SpringApplication.run(AlanRegistryServer.class);
    }
}
