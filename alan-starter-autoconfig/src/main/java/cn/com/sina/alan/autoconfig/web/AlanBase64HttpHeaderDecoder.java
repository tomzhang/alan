package cn.com.sina.alan.autoconfig.web;

import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.common.exception.AlanException;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 10/11/2016.
 */
public class AlanBase64HttpHeaderDecoder implements AlanHttpHeaderDecoder {
    private static final Logger log = LoggerFactory.getLogger(AlanBase64HttpHeaderDecoder.class);

    @Override
    public AlanException decode(Response response) {
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
