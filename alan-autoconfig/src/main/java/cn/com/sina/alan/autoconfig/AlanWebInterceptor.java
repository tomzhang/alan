package cn.com.sina.alan.autoconfig;

import cn.com.sina.alan.common.http.ResponseResult;
import cn.com.sina.alan.common.utils.HttpUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by whf on 8/17/16.
 */
@Component
public class AlanWebInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpUtils.putResponseStatus(response, ResponseResult.getSuccessResult());

        return super.preHandle(request, response, handler);
    }
}
