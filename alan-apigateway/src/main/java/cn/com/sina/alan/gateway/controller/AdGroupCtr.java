package cn.com.sina.alan.gateway.controller;

import cn.com.sina.alan.common.exception.AlanException;
import cn.com.sina.alan.ms.ea.api.service.AdvertGroupRemoteService;
import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by whf on 8/5/16.
 */
@RestController
public class AdGroupCtr {
    @Value("${config.timeout}")
    private Integer timeout;


    @Autowired
    private AdvertGroupRemoteService remoteService;


    @RequestMapping(value = "/")
    public String home() {
        return timeout.toString();
    }

    @RequestMapping(value = "/group/{id}")
    public AdvertGroupVO findAdGroup(@PathVariable("id") Integer groupId) throws AlanException {
        return remoteService.findByGroupId(groupId);
    }
}
