package cn.com.sina.alan.autoconfig.feign;

import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by whf on 8/12/16.
 */
@Configuration
public class AlanFeignConfig {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    @ConditionalOnProperty(prefix = "alan.auto", name = "feignDecoder", havingValue = "true", matchIfMissing = true)
    public Decoder feignDecoder() {
        return new AlanFeignDecoder(new SpringDecoder(messageConverters));
    }

    @Bean
    @ConditionalOnProperty(prefix = "alan.auto", name = "feignErrorDecoder", havingValue = "true", matchIfMissing = true)
    public ErrorDecoder errorDecoder() {
        return new AlanFeignErrorDecoder();
    }
}
