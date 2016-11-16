package cn.com.sina.alan.autoconfig.feign;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.form.spring.SpringMultipartEncodedDataProcessor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.http.HttpMethod;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
            MultipartFile file = (MultipartFile)  requestBody;
            //processMultipartFile((MultipartFile) requestBody, request);

            Map<String, Object> data = Collections.singletonMap(file.getName(), requestBody);
            new SpringMultipartEncodedDataProcessor().process(data, request);

        } else {
            // 是POJO对象
            processPOJO(requestBody, request);

        }


    }

    /**
     * 将byte数组取出, 通过Base64编码后放入请求体中
     * @deprecated
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

    /**
     * 将POJO转换成键值对放到HTTP请求中;
     * 如果是GET请求则添加到URL中, 如果是POST请求则添加到请求体中
     *
     * @param requestBody
     * @param request
     */
    private void processPOJO(Object requestBody, RequestTemplate request) {
        // 将POJO的属性转换成键值对
        Map<String, String> parameterMap = parseParameterMap(requestBody);

        // 判断是否为GET请求
        if (isGetMethod(request)) {
            // k-v放到URL中
            parameterMap.entrySet().stream().forEach( entry -> {
                request.query(false, entry.getKey(), entry.getValue());
            } );

            log.debug("将参数 {} 添加到URL中", parameterMap);

        } else {
            // 非GET请求, 参数扔到请求体中
            String queryString = buildQueryString(parameterMap);
            request.header("Content-Type", "application/x-www-form-urlencoded");
            request.body(queryString);

            log.debug("将参数 {} 添加到请求体中", queryString);
        }

    }

    private boolean isGetMethod(RequestTemplate request) {
        return request.method().equals(HttpMethod.GET.name());
    }

    /**
     * 将POJO转换成key-value参数对
     * @param body
     * @return
     */
    private Map<String, String> parseParameterMap(Object body) {
        Map<String, String> parameterMap = new HashMap<>();

        ReflectionUtils.doWithFields(body.getClass(), field -> {
            field.setAccessible(true);

            Object value = field.get(body);
            if (null != value) {

                String name = field.getName();
                parameterMap.put(name, value.toString());

                log.debug("从POJO中解析出参数: {}={}", name, value.toString());
            }
        });

        return parameterMap;
    }

    /**
     * 将参数对转换为query string, 如 a=b&c=d&e=f
     * @param parameterMap
     * @return
     */
    private String buildQueryString(Map<String, String> parameterMap) {
        StringBuilder sb = new StringBuilder(parameterMap.size() * 8);

        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }

        return sb.toString();
    }

}
