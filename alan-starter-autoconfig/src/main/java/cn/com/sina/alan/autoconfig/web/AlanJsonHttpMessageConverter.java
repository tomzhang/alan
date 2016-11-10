package cn.com.sina.alan.autoconfig.web;

import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.common.http.ResponseResult;
import cn.com.sina.alan.common.utils.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AlanHttpHeaderEncoder headerEncoder;

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        // controller返回ResponseResult说明有业务错误
        if (object instanceof ResponseResult) {

            ResponseResult result = (ResponseResult) object;
            // 设置响应头
            headerEncoder.encodeException(result, outputMessage.getHeaders());

            log.debug("添加响应头 {}", result);
            object = "";

        } else {
            // controller正常返回
            headerEncoder.encodeNormal(object, outputMessage.getHeaders());
            log.debug("添加响应头 {}", ResponseResult.getSuccessResult());
        }

        outputMessage.getHeaders().set(Const.HeaderParam.IP, getLocalIP());

        super.writeInternal(object, type, outputMessage);
    }

    private String getLocalIP() {
        return NetUtils.getLocalAddress().getHostAddress();
    }

}
