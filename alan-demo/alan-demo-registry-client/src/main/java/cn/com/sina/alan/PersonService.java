package cn.com.sina.alan;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 向服务名为"person"的服务发起"/find"请求
 * @FeignClient中的fallback为远程调用失败时执行的方法
 *
 * Created by whf on 7/28/16.
 */
@FeignClient(name = "person", fallback = FeignHystrixFallback.class)
public interface PersonService {

    /**
     * 远程调用接口
     * @param name
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    Person findPerson(@RequestParam("name") String name);
}

