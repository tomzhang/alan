package cn.com.sina.alan.autoconfig.web;

import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.common.http.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 添加特定的HTTP头信息
 * Created by whf on 8/18/16.
 */
public class AlanJsonHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public static final Logger log = LoggerFactory.getLogger(AlanJsonHttpMessageConverter.class);

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        // controller返回ResponseResult说明有业务错误
        if (object instanceof ResponseResult) {

            ResponseResult result = (ResponseResult) object;
            putHeader(result, outputMessage.getHeaders());
            result.getResponse().setStatus(Const.HTTP_STATUS_BUSINESS_ERROR);

            log.debug("添加响应头 {}", result);
            object = "";
        } else {
            // controller正常返回
            putHeader(ResponseResult.getSuccessResult(), outputMessage.getHeaders());
        }

        super.writeInternal(object, type, outputMessage);
    }

    private void putHeader(ResponseResult result, HttpHeaders headers) {
        headers.set(Const.HeaderParam.CODE, result.getCodeAsString());
        headers.set(Const.HeaderParam.MESSAGE, result.getMessage());

        headers.setAll(result.getCustomHeaders());

    }
}
