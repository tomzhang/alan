package cn.com.sina.autoconfig;

import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.common.exception.AlanException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * Created by whf on 8/12/16.
 */
public class AlanFeignDecoder extends ResponseEntityDecoder {
    private static final Logger log = LoggerFactory.getLogger(AlanFeignDecoder.class);

    public AlanFeignDecoder(Decoder decoder) {
        super(decoder);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException {
        Map<String, Collection<String>> headers = response.headers();
        String code = getHeaderValue(headers.get(Const.HeaderParam.CODE));
        String msg = getHeaderValue(headers.get(Const.HeaderParam.MESSAGE));

        System.out.println("OKOKKOKOK");
        log.debug("解码: code = {}, msg = {}", code, msg);


        if (false == code.equals("0")) {
            throw new AlanException(Integer.parseInt(code), msg);
        }

        return super.decode(response, type);
    }

    private String getHeaderValue(Collection<String> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return "";
        }

        return collection.iterator().next();
    }
}