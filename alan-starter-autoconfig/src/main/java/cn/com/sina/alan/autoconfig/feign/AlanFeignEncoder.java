package cn.com.sina.alan.autoconfig.feign;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

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

        // 如果是MultipartFile类型的参数
        // 说明该参数是要上传的文件
        if (requestBody instanceof MultipartFile) {
            processMultipartFile((MultipartFile) requestBody, request);

        } else {
            // 是POJO对象
            processPOJO(requestBody, request);

        }


    }

    /**
     * 将byte数组取出, 通过Base64编码后放入请求体中
     * @param file
     * @param request
     */
    private void processMultipartFile(MultipartFile file, RequestTemplate request) {
        try {
            byte[] buf = file.getBytes();
            byte[] base64Buf = Base64.encodeBase64(buf);

            request.header("Content-Type", "text/plain");
            request.body(base64Buf, Charset.forName("UTF-8"));

        } catch (IOException e) {
            e.printStackTrace();
            throw new EncodeException(e.getMessage());
        }
    }

    private void processPOJO(Object requestBody, RequestTemplate request) {
        StringBuilder sb = new StringBuilder();
        ReflectionUtils.doWithFields(requestBody.getClass(), field -> {
            field.setAccessible(true);

            Object value = field.get(requestBody);
            if (null != value) {
                String name = field.getName();
                log.debug("添加参数{}={}", name, value);
                //request.query(name, value.toString());

                sb.append(name);
                sb.append("=");
                sb.append(value.toString());
                sb.append("&");
            }
        });

        String postBody = sb.toString();
        log.debug("POST请求体:{}", postBody);

        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.body(postBody);
    }

}
