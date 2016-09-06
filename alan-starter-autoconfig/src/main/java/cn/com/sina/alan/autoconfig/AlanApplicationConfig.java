package cn.com.sina.alan.autoconfig;

import cn.com.sina.alan.autoconfig.feign.AlanFeignConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * 免去添加@EnableXXX注解
 * Created by whf on 8/19/16.
 */
@Configuration
@EnableEurekaClient
//@EnableFeignClients(basePackages = "cn.com.sina.alan", defaultConfiguration = AlanFeignConfig.class)
@EnableFeignClients(basePackages = "cn.com.sina.alan")
@EnableHystrix

@PropertySources({
    @PropertySource("classpath:alan.properties")
})

// 只有当alan.auto.enable=true或没有时才应用该配置类
@ConditionalOnProperty(prefix = "alan.auto", name = "enable", matchIfMissing = true, havingValue = "true")
public class AlanApplicationConfig {
}
