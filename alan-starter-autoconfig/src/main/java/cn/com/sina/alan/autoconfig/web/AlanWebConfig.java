package cn.com.sina.alan.autoconfig.web;

import cn.com.sina.alan.autoconfig.monitor.AlanFlowMonitorInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Alan微服务公共配置类
 * Created by whf on 8/17/16.
 */
@Configuration
public class AlanWebConfig extends WebMvcConfigurerAdapter {
    public static final Logger log = LoggerFactory.getLogger(AlanWebConfig.class);

    @Autowired
    private AlanHttpClientProperties httpClientProperties;


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
    @ConditionalOnProperty(prefix = "alan.auto", name = "alanHttpClient", havingValue = "true", matchIfMissing = true)
    public HttpClient httpClient() {
        log.info("AlanHttpClient已启用. so_timeout = {}, conn_timeout = {}", httpClientProperties.getReadTimeout(), httpClientProperties.getConnectionTimeout());

        return HttpClients.custom()
                .setDefaultRequestConfig(httpRequestConfig())
                .build();
    }

    @Bean
    //@ConditionalOnMissingClass("cn.com.sina.alan.gateway.ApiGatewayApplication")
    @ConditionalOnMissingBean
    public AlanHttpHeaderEncoder alanHttpHeaderEncoder() {
        log.info("{}已经启用", "AlanBase64HttpHeaderEncoder");

        return new AlanBase64HttpHeaderEncoder();
    }

    @Bean
    //@ConditionalOnMissingClass("cn.com.sina.alan.gateway.ApiGatewayApplication")
    @ConditionalOnMissingBean
    public AlanHttpHeaderDecoder alanHttpHeaderDecoder() {
        log.info("{}已启用", "AlanBase64HttpHeaderDecoder");

        return new AlanBase64HttpHeaderDecoder();
    }



    @Autowired(required = false)
    private AlanFlowMonitorInterceptor alanFlowMonitorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (null != alanFlowMonitorInterceptor) {
            log.info("请求计数服务已启动");
            registry.addInterceptor(alanFlowMonitorInterceptor);
        }
    }

    public RequestConfig httpRequestConfig() {


        return RequestConfig.custom()
                .setSocketTimeout(httpClientProperties.getReadTimeout())
                .setConnectTimeout(httpClientProperties.getConnectionTimeout())
                .build();
    }

}
