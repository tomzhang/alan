package cn.com.sina.alan.autoconfig.feign;

import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.common.exception.AlanException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by whf on 8/17/16.
 */
public class AlanFeignErrorDecoder implements ErrorDecoder {
    public static final Logger log = LoggerFactory.getLogger(AlanFeignDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        if (status >= 500 && status < 600) {
            log.error("HTTP远程调用出错, 状态码{}", status);
            return new IllegalStateException("HTTP远程调用出错");
        }

        return new AlanException(
                Integer.parseInt(response.headers().get(Const.HeaderParam.CODE).iterator().next()),
                response.headers().get(Const.HeaderParam.MESSAGE).iterator().next());
    }
}
