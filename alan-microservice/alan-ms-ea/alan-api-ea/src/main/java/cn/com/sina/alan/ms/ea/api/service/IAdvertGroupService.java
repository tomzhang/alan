package cn.com.sina.alan.ms.ea.api.service;

import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by whf on 8/9/16.
 */
@FeignClient(name = "ea")
public interface IAdvertGroupService {

    @RequestMapping(value = "/group/{groupId}")
    AdvertGroupVO findByGroupId(@PathVariable("groupId") Integer adGroupId);
}
