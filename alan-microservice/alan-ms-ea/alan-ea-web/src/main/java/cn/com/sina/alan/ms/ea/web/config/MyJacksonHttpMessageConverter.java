package cn.com.sina.alan.ms.ea.web.config;

import cn.com.sina.alan.common.vo.AlanResponse;
import cn.com.sina.alan.common.vo.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 自定义Jackson序列化过程
 * Created by whf on 8/11/16.
 */
public class MyJacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    private static final Logger log = LoggerFactory.getLogger(MyJacksonHttpMessageConverter.class);

    /**
     * 在原来的返回对象中包装一层Wrapper对象
     * @param object 控制器返回的对象
     * @param type
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (object instanceof AlanResponse) {
            log.debug("Controller写回 AlanResponse对象, 不进行封装");
            super.writeInternal(object, type, outputMessage);

            return;
        }

        // 用wrapper封装控制器返回的对象
        super.writeInternal(new ResponseWrapper(object), type, outputMessage);
    }

}
