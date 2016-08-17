package cn.com.sina.alan.autoconfig;

import cn.com.sina.alan.common.exception.AlanException;
import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * Created by whf on 8/17/16.
 */
public class AlanFeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return new AlanException();
    }
}
