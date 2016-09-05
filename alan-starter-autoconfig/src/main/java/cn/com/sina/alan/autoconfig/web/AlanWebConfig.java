package cn.com.sina.alan.autoconfig.web;

import feign.Feign;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

    public static int HTTP_SO_TIMEOUT = 5;
    public static int HTTP_CONN_TIMEOUT = 5;


    /**
     * 只有当不是ApiGateway时才有效
     * @return
     */
    @Bean
    @ConditionalOnMissingClass("cn.com.sina.alan.gateway.ApiGatewayApplication")
    @ConditionalOnProperty(prefix = "alan.auto", name = "alanHttpMessageConverter", havingValue = "true", matchIfMissing = true)
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        log.info("AlanJsonHttpMessageConverter已启用");
        return new AlanJsonHttpMessageConverter();
    }

    @Bean
    @ConditionalOnClass(Feign.class)
    public HttpClient httpClient() {
        log.info("AlanHttpClient已启用. so_timeout = {}, conn_timeout = {}", HTTP_SO_TIMEOUT, HTTP_CONN_TIMEOUT);

        return HttpClients.custom()
                .setDefaultRequestConfig(httpRequestConfig())
                .build();
    }

    public RequestConfig httpRequestConfig() {


        return RequestConfig.custom()
                .setSocketTimeout(HTTP_SO_TIMEOUT)
                .setConnectTimeout(HTTP_CONN_TIMEOUT)
                .build();
    }

}
