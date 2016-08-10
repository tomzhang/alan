package cn.com.sina.alan.gateway.controller;

import cn.com.sina.alan.gateway.service.FeignService;
import cn.com.sina.alan.ms.ea.api.service.AdvertGroupRemoteService;
import cn.com.sina.alan.ms.ea.api.service.HelloService;
import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by whf on 8/5/16.
 */
@RestController
public class TestCtr {
    @Value("${config.timeout}")
    private Integer timeout;

    //private HelloService helloService;
    //@Qualifier("eaFeignClient")
    @Autowired
    private AdvertGroupRemoteService advertGroupRemoteService;


/*    @Autowired
    private FeignService feignService;*/

    @RequestMapping(value = "/")
    public String home() {
        return timeout.toString();
    }

    @RequestMapping(value = "/group/{id}")
    public AdvertGroupVO findAdGroup(@PathVariable("id") Integer groupId) {
        //return adGroupService.findByGroupId(groupId);
        return advertGroupRemoteService.findByGroupId(groupId);
    }
}
