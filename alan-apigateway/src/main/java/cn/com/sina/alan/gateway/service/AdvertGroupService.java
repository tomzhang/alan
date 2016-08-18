package cn.com.sina.alan.gateway.service;

import cn.com.sina.alan.ms.ea.api.AdvertGroupRemoteService;
import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by whf on 8/18/16.
 */
@Service
public class AdvertGroupService {
    @Autowired
    private AdvertGroupRemoteService adGroupRemoteService;

    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
    })
    public AdvertGroupVO findById(Integer groupId) {
        return adGroupRemoteService.findByGroupId(groupId);
    }

    private AdvertGroupVO fallback(Integer id) {
        AdvertGroupVO vo = new AdvertGroupVO();
        vo.setGroupName("fallback");

        return vo;
    }
}
