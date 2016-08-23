package cn.com.sina.alan.autoconfig.feign;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Type;

/**
 * Created by whf on 8/22/16.
 */
public class AlanFeignEncoder extends SpringEncoder {
    private static final Logger log = LoggerFactory.getLogger(AlanFeignEncoder.class);


    public AlanFeignEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    public void encode(Object requestBody, Type bodyType, RequestTemplate request) throws EncodeException {
        log.debug("编码{}对象,类型为{}", requestBody, bodyType);

        ReflectionUtils.doWithFields(requestBody.getClass(), field -> {
            field.setAccessible(true);

            Object value = field.get(requestBody);
            if (null != value) {
                String name = field.getName();
                log.debug("添加参数{}={}", name, value);
                request.query(name, value.toString());
            }
        });

        //super.encode(requestBody, bodyType, request);
    }

}
