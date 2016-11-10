package cn.com.sina.alan.autoconfig.web;

import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.common.http.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletResponse;

/**
 * 将message使用Base64编码后放入响应头中
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 10/11/2016.
 */
public class AlanBase64HttpHeaderEncoder implements AlanHttpHeaderEncoder {
    private static final Logger log = LoggerFactory.getLogger(AlanBase64HttpHeaderEncoder.class);

    @Override
    public void encodeException(ResponseResult rs, HttpHeaders headers) {
        // 设置响应头
        putHeader(rs, headers);

        HttpServletResponse response = rs.getResponse();

        // 如果远程HTTP调用状态码不在400~500之间, 则重置状态码为HTTP_STATUS_BUSINESS_ERROR
        if ( !(response.getStatus() >= 400 && response.getStatus() < 500) ) {
            // 设置状态码
            rs.getResponse().setStatus(Const.HTTP_STATUS_BUSINESS_ERROR);
        }

    }

    @Override
    public void encodeNormal(Object resultObject, HttpHeaders headers) {
        putHeader(ResponseResult.getSuccessResult(), headers);
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
