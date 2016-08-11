package cn.com.sina.alan.ms.ea;

import cn.com.sina.alan.ms.ea.web.config.MyJacksonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by whf on 8/9/16.
 */
@SpringBootApplication
@EnableEurekaClient
//@EnableFeignClients
public class AlanMsEaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlanMsEaApplication.class, args);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MyJacksonHttpMessageConverter();
    }
}
