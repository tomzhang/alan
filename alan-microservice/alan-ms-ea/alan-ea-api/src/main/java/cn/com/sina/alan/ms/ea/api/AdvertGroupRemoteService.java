package cn.com.sina.alan.ms.ea.api;

import cn.com.sina.alan.common.exception.AlanException;
import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import cn.com.sina.alan.common.vo.ResponseWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by whf on 8/9/16.
 */
@ConditionalOnMissingClass("cn.com.sina.alan.ms.ea.AlanMsEaApplication")
@FeignClient(name = "ea")
public interface AdvertGroupRemoteService {

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
    AdvertGroupVO findByGroupId(@PathVariable("groupId") Integer adGroupId)
            throws AlanException;

/*    @RequestMapping(value = "/group/list", method = RequestMethod.GET)
    ResponseWrapper<List<AdvertGroupVO>> list(AdvertGroupVO vo);*/
}
