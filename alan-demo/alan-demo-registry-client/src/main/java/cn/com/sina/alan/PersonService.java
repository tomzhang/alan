package cn.com.sina.alan;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 向服务名为"person"的服务发起"/find"请求
 * Created by whf on 7/28/16.
 */
@FeignClient("person")
public interface PersonService {

    /**
     * 远程调用接口
     * @param name
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public Person findPerson(@RequestParam("name") String name);
}
