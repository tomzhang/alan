package cn.com.sina.alan.ms.ea.api.service;

import cn.com.sina.alan.common.exception.AlanException;
import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import cn.com.sina.alan.ms.ea.api.vo.ResponseWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by whf on 8/9/16.
 */
@ConditionalOnMissingClass("cn.com.sina.alan.ms.ea.AlanMsEaApplication")
@FeignClient(name = "ea")
public interface AdvertGroupRemoteService {

    @RequestMapping(value = "/group/{groupId}")
    ResponseWrapper<AdvertGroupVO> findByGroupId(@PathVariable("groupId") Integer adGroupId)
            throws AlanException;
}
