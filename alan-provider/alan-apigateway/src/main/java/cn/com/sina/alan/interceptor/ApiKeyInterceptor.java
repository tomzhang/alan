package cn.com.sina.alan.interceptor;

import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.common.exception.AlanException;
import cn.com.sina.alan.common.exception.MissingRequestParmException;
import cn.com.sina.alan.common.exception.RequestTimeoutException;
import cn.com.sina.alan.common.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by whf on 8/5/16.
 */
@Component
public class ApiKeyInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(ApiKeyInterceptor.class);
    
    @Value("${config.timeout}")
    private Integer timeout;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 取出请求参数
        SecurityParam securityParam = retrieveSecurityParam(request);

        // 验证请求是否超时
        checkRequestTimeout(securityParam);
        // 检查签名
        checkSign(securityParam, request);


        return super.preHandle(request, response, handler);
    }

    /**
     * 检查签名
     * @param securityParam
     * @param req
     */
    private void checkSign(SecurityParam securityParam, HttpServletRequest req) {
        log.debug("检查签名: {}", securityParam.sign);

    }


    /**
     * 验证请求是否超时
     * @param securityParam
     * @throws AlanException
     */
    private void checkRequestTimeout(SecurityParam securityParam) throws AlanException {
        long timestamp = securityParam.timestamp;
        log.debug("验证时间戳: {}", timestamp);
        
        boolean isTimeout = TimeUtils.isIntervalMoreThan(timestamp, timeout.intValue());
        if (isTimeout) {
            // 请求超时
            log.info("时间戳 {} 超时", timestamp);
            throw new RequestTimeoutException();
        }

    }

    /**
     * 取出sign和timestamp参数
     * @param req
     * @return
     * @throws AlanException
     */
    private SecurityParam retrieveSecurityParam(HttpServletRequest req) throws AlanException {
        String sign = req.getParameter(Const.RequestParam.SIGN);
        String strTimestamp = req.getParameter(Const.RequestParam.TIMESTAMP);

        if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(strTimestamp)) {
            throw new MissingRequestParmException("sign and timestamp is required");
        }



        return new SecurityParam(sign, Long.parseLong(strTimestamp));
    }

    private static class SecurityParam {
        public SecurityParam(String sign, long timestamp) {

        }

        public String sign;
        public long timestamp;
    }
}
