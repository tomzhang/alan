package cn.com.sina.alan.gateway;

import cn.com.sina.autoconfig.AlanFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by whf on 8/5/16.
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.com.sina.alan")
@EnableEurekaClient
@EnableFeignClients(basePackages = "cn.com.sina.alan", defaultConfiguration = AlanFeignConfig.class)
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
