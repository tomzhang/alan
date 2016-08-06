package cn.com.sina.alan.common.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by whf on 8/6/16.
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.com.sina.alan")
public class AlanCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlanCommonApplication.class, args);
    }

}
