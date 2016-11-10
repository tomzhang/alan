package cn.com.sina.alan.autoconfig.web;

import cn.com.sina.alan.common.http.ResponseResult;
import org.springframework.http.HttpHeaders;

/**
 * Http请求头编码器, 用于将微服务响应状态信息封装到HTTP响应头中
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 10/11/2016.
 */
public interface AlanHttpHeaderEncoder {
    /**
     * 将ResponseResult对象编入响应头中, 当抛出AlanException时会调用
     * @param rs
     * @param headers
     */
    void encodeException(ResponseResult rs, HttpHeaders headers);

    /**
     * 正常返回数据对象时调用
     * @param resultObject
     * @param headers
     */
    void encodeNormal(Object resultObject, HttpHeaders headers);
}
