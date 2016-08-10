package cn.com.sina.alan.ms.ea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by whf on 8/9/16.
 */
@SpringBootApplication
@EnableEurekaClient
public class AlanMsEaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlanMsEaApplication.class, args);
    }
}
