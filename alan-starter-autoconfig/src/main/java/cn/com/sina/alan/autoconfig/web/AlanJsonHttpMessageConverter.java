package cn.com.sina.alan.autoconfig.web;

import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.common.http.ResponseResult;
import cn.com.sina.alan.common.utils.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletResponse;
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
            // 设置响应头
            putHeader(result, outputMessage.getHeaders());

            HttpServletResponse response = result.getResponse();

            // 如果远程HTTP调用状态码不在400~500之间, 则重置状态码为HTTP_STATUS_BUSINESS_ERROR
            if ( !(response.getStatus() >= 400 && response.getStatus() < 500) ) {
                // 设置状态码
                result.getResponse().setStatus(Const.HTTP_STATUS_BUSINESS_ERROR);
            }

            log.debug("添加响应头 {}", result);
            object = "";

        } else {
            // controller正常返回
            putHeader(ResponseResult.getSuccessResult(), outputMessage.getHeaders());
            log.debug("添加响应头 {}", ResponseResult.getSuccessResult());
        }

        outputMessage.getHeaders().set(Const.HeaderParam.IP, getLocalIP());

        super.writeInternal(object, type, outputMessage);
    }

    private String getLocalIP() {
        return NetUtils.getLocalAddress().getHostAddress();
    }

    private void putHeader(ResponseResult result, HttpHeaders headers) {
        String encodedMessage = Base64Utils.encodeToString(result.getMessage().getBytes());
        log.debug("message base64编码: {}", encodedMessage);
        result.setMessage(encodedMessage);

        headers.set(Const.HeaderParam.CODE, result.getCodeAsString());
        headers.set(Const.HeaderParam.MESSAGE, encodedMessage);

        headers.setAll(result.getCustomHeaders());

    }
}
