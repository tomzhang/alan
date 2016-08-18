package cn.com.sina.alan.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Alan微服务公共配置类
 * Created by whf on 8/17/16.
 */
@Configuration
public class AlanWebConfig extends WebMvcConfigurerAdapter {
    public static final Logger log = LoggerFactory.getLogger(AlanWebConfig.class);


    /**
     * 只有当不是ApiGateway时才有效
     * @return
     */
    @Bean
    @ConditionalOnMissingClass("cn.com.sina.alan.gateway.ApiGatewayApplication")
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        log.info("AlanJsonHttpMessageConverter已启用");
        return new AlanJsonHttpMessageConverter();
    }

}
