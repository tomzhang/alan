package cn.com.sina.alan.gateway.interceptor;

import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.common.exception.AlanException;
import cn.com.sina.alan.common.exception.ApiKeyException;
import cn.com.sina.alan.common.exception.MissingRequestParmException;
import cn.com.sina.alan.common.exception.RequestTimeoutException;
import cn.com.sina.alan.gateway.service.SecurityService;
import cn.com.sina.alan.common.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by whf on 8/5/16.
 */
@Component
public class ApiKeyInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(ApiKeyInterceptor.class);
    
    @Value("${config.timeout}")
    private Integer timeout;

    @Autowired
    private SecurityService securityService;

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
    private void checkSign(SecurityParam securityParam, HttpServletRequest req) throws AlanException {
        log.debug("检查签名: {}", securityParam.sign);
        Map<String, String> parmMap = req.getParameterMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()[0]));

        boolean result = securityService.checkSign(parmMap, securityParam.pubKey, securityParam.sign);
        log.info("签名验证结果:{}, pub_key = {}, sign = {}", result, securityParam.pubKey, securityParam.sign);

        if (false == result) {
            throw new ApiKeyException("签名验证失败");
        }
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
        String pubKey = req.getParameter(Const.RequestParam.PUBLIC_KEY);

        if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(strTimestamp)
                || StringUtils.isEmpty(pubKey)) {
            throw new MissingRequestParmException("sign, pk and timestamp is required");
        }



        return new SecurityParam(sign, Long.parseLong(strTimestamp), pubKey);
    }

    private static class SecurityParam {
        public SecurityParam(String sign, long timestamp, String pubKey) {
            this.sign = sign;
            this.timestamp = timestamp;
            this.pubKey = pubKey;
        }

        public String sign;
        public long timestamp;
        public String pubKey;
    }
}
