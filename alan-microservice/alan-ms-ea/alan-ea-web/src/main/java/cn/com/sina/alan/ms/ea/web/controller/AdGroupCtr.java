package cn.com.sina.alan.ms.ea.web.controller;

import cn.com.sina.alan.common.exception.AlanException;
import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import cn.com.sina.alan.ms.ea.service.api.AdvertGroupAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by whf on 8/9/16.
 */
@RestController
public class AdGroupCtr {
    @Autowired
    private AdvertGroupAPIService adGroupService;

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
    public AdvertGroupVO findByGroupId(@PathVariable("groupId") Integer groupId) throws AlanException {
        return adGroupService.findByGroupId(groupId);
    }
}
