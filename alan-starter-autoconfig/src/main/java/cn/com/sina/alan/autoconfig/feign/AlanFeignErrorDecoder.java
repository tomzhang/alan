package cn.com.sina.alan.autoconfig.feign;

import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.common.exception.AlanException;
import cn.com.sina.alan.common.exception.AlanInvalidRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * Feign的错误处解码器。只有当状态码非2XX时才会执行
 * Created by whf on 8/17/16.
 */
public class AlanFeignErrorDecoder implements ErrorDecoder {
    public static final Logger log = LoggerFactory.getLogger(AlanFeignErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();

        // 处理5xx错误
        if (status >= 500 && status < 600) {
            log.error("HTTP远程调用出错, 状态码{}", status);
            return new IllegalStateException("HTTP远程调用出错");
        }

        // 处理4xx错误
        if (status >= 400 && status < 500) {
            log.error("HTTP远程调用出错, 状态码{}", status);
            return new AlanInvalidRequestException("Invalid HTTP Request");
        }


        return new AlanException(getCode(response), getMessage(response));
    }

    private int getCode(Response response) {
        Collection<String> codeParam = response.headers().get(Const.HeaderParam.CODE);
        if (CollectionUtils.isEmpty(codeParam)) {
            log.error("响应头没有code");
            return -1;
        }

        int code = Integer.parseInt(codeParam.iterator().next());

        return code;
    }

    private String getMessage(Response response) {
        Collection<String> msgParam = response.headers().get(Const.HeaderParam.MESSAGE);
        if (CollectionUtils.isEmpty(msgParam)) {
            log.error("响应头没有message");
            return "";
        }

        String encodedMessage = msgParam.iterator().next();
        // 使用Base64解码message
        String decodedMessage = decodeMessage(encodedMessage);

        log.debug("解码message: {}", decodedMessage);

        return decodedMessage;
    }

    private String decodeMessage(String message) {
        byte[] decodedMessageBuf = Base64Utils.decodeFromString(message);
        return new String(decodedMessageBuf);
    }
}
