package cn.com.sina.alan.autoconfig.web;

import cn.com.sina.alan.common.exception.AlanException;
import feign.Response;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 10/11/2016.
 */
public interface AlanHttpHeaderDecoder {
    AlanException decode(Response response);
}
