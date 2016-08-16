package cn.com.sina.alan.common.utils;

import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.common.exception.AlanInvalidResponseException;
import cn.com.sina.alan.common.http.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by whf on 8/16/16.
 */
public class HttpUtils {
    private HttpUtils() {}

    /**
     * 从响应头中取出结果
     * @return
     * @throws AlanInvalidResponseException 请求头中没有code或者不是数字
     */
    public static ResponseResult getResponseStatus(HttpServletResponse resp) throws AlanInvalidResponseException {
        int code = getCode(resp);
        String msg = getMessage(resp);

        // 取出自定义头(if any)
        Map<String, String> customHeaders = getCustomHeaders(resp);
        if (null != customHeaders) {
            return new ResponseResult(code, msg, customHeaders);
        }

        return new ResponseResult(code, msg);

    }

    /**
     * 将处理结果放到响应返回头中
     * @param resp
     * @param result
     */
    public static void putResponseStatus(HttpServletResponse resp, ResponseResult result) {
        if (null == result) {
            throw new IllegalArgumentException("result不能为null");
        }

        // 放入标准头
        resp.setHeader(Const.HeaderParam.CODE, result.getCodeAsString());
        resp.setHeader(Const.HeaderParam.MESSAGE, result.getMessage());

        // 放入自定义头
        if (null != result.getCustomHeaders()) {
            result.getCustomHeaders().entrySet().forEach( entry -> resp.setHeader(entry.getKey(), entry.getValue()));
        }
    }

    /**
     * 从响应头中取出自定义头
     * @param resp
     * @return
     */
    private static Map<String, String> getCustomHeaders(HttpServletResponse resp) {
        Map<String, String> ret = null;

        Collection<String> headerNames = resp.getHeaderNames();
        for (String header : headerNames ) {
            if (header.startsWith(Const.HeaderParam.PREFIX)) {
                if (null == ret) {
                    ret = new HashMap<>(3);
                }

                ret.put(header, resp.getHeader(header));
            }
        }

        return ret;
    }

    /**
     * 从响应头中取出code
     * @return
     * @throws AlanInvalidResponseException
     */
    private static int getCode(HttpServletResponse resp) throws AlanInvalidResponseException {
        // 取出code值
        String strCode = resp.getHeader(Const.HeaderParam.CODE);

        if (null == strCode || strCode.isEmpty()) {
            throw new AlanInvalidResponseException("code不存在");
        }

        try {
            return Integer.parseInt(strCode);

        } catch (NumberFormatException ex) {
            throw new AlanInvalidResponseException("code格式错误");
        }

    }

    /**
     * 从响应头中取出message
     * @return
     * @throws AlanInvalidResponseException
     */
    private static String getMessage(HttpServletResponse resp) throws AlanInvalidResponseException {
        String msg = resp.getHeader(Const.HeaderParam.MESSAGE);

        if (null == msg || msg.isEmpty()) {
            throw new AlanInvalidResponseException("msg不存在");
        }

        return msg;
    }

}
