package cn.com.sina.alan.autoconfig.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wanghongfei on 27/04/2017.
 */
@Component
@ConditionalOnProperty(prefix = "alan.auto.monitor", name = "flow", havingValue = "true", matchIfMissing = false)
public class AlanFlowMonitorInterceptor extends HandlerInterceptorAdapter {
    public static final Logger log = LoggerFactory.getLogger(AlanFlowMonitorInterceptor.class);



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AlanRequestCounter.reqCount.incrementAndGet();

        return super.preHandle(request, response, handler);
    }

}
