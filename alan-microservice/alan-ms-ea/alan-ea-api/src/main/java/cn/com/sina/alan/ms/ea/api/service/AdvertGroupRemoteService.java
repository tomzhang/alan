package cn.com.sina.alan.ms.ea.api.service;

import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import cn.com.sina.alan.ms.ea.api.vo.ResponseWrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by whf on 8/9/16.
 */
@FeignClient(name = "ea")
public interface AdvertGroupRemoteService {

    @RequestMapping(value = "/group/{groupId}")
    ResponseWrapper findByGroupId(@PathVariable("groupId") Integer adGroupId);
}
