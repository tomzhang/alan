package cn.com.sina.alan.autoconfig.feign;

import cn.com.sina.alan.autoconfig.utils.ObjectUtils;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.form.spring.SpringMultipartEncodedDataProcessor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.json.JsonObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Feign参数编码器; 将Feign接口中声明的参数编码至HTTP请求参数中
 * Created by whf on 8/22/16.
 */
public class AlanFeignEncoder extends SpringEncoder {
    private static final Logger log = LoggerFactory.getLogger(AlanFeignEncoder.class);

/*    @Autowired
    private AlanDateProperties alanDateProperties;*/

    @Autowired
    private ObjectUtils objectUtils;


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

        } else if (requestBody instanceof JsonObject) {
            processJsonObject( (JsonObject) requestBody, request );

        } else {
            // 是POJO对象
            processPOJO(requestBody, request);

        }


    }

    /**
     * 将json串取出放入请求体中
     * @param jsonObject
     * @param template
     */
    private void processJsonObject(JsonObject jsonObject, RequestTemplate template) {
        template.header(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE);

        template.body(jsonObject.toString());

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

            request.header(HttpHeaders.CONTENT_TYPE, "text/plain");
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
    protected void processPOJO(Object requestBody, RequestTemplate request) {
        // 将POJO的属性转换成键值对
        Map<String, String> parameterMap = parseParameterMap(requestBody);

        // 判断是否为类GET请求
        if (isBodylessMethod(request)) {
            // k-v放到URL中
            parameterMap.entrySet().stream().forEach( entry -> {
                request.query(false, entry.getKey(), entry.getValue());
            } );

            log.debug("将参数 {} 添加到URL中", parameterMap);

        } else {
            // 这是带有body的请求, 参数扔到请求体中
            String queryString = null;
            try {
                queryString = objectUtils.convertToHttpFormParameter(parameterMap, "UTF8");

            } catch (UnsupportedEncodingException e) {
                // 不可能发生
                e.printStackTrace();
                throw new IllegalStateException("发生了不可能发生的错误!");
            }

            request.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
            request.body(queryString);

            log.debug("将参数 {} 添加到请求体中", queryString);
        }

    }

    private boolean isBodylessMethod(RequestTemplate request) {
        String method = request.method();

        return method.equals(HttpMethod.GET.name())
                || method.equals(HttpMethod.HEAD.name())
                || method.equals(HttpMethod.TRACE.name())
                || method.equals(HttpMethod.OPTIONS.name());
    }

    /**
     * 将POJO转换成key-value参数对
     * @param body
     * @return
     */
    protected Map<String, String> parseParameterMap(Object body) {
        return objectUtils.convertToMap(body);
    }

}
