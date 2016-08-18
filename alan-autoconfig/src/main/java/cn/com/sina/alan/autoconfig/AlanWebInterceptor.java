package cn.com.sina.alan.autoconfig;

import cn.com.sina.alan.common.config.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by whf on 8/18/16.
 */
public class AlanWebInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (false == response.getHeader(Const.HeaderParam.CODE).equals("0")) {
            response.setStatus(600);
        }

        super.afterCompletion(request, response, handler, ex);
    }
}
