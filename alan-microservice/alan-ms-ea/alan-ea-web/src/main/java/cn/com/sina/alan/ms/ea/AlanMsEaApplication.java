package cn.com.sina.alan.ms.ea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by whf on 8/9/16.
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = "cn.com.sina.alan")
//@EnableFeignClients
public class AlanMsEaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlanMsEaApplication.class, args);
    }


}
