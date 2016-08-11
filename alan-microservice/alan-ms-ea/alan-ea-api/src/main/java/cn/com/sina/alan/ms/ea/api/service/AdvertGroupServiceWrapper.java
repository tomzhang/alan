package cn.com.sina.alan.ms.ea.api.service;

import cn.com.sina.alan.common.exception.AlanException;
import cn.com.sina.alan.common.exception.EntityNotFoundException;
import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import cn.com.sina.alan.ms.ea.api.vo.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whf on 8/11/16.
 */
@ConditionalOnMissingClass("cn.com.sina.alan.ms.ea.AlanMsEaApplication")
@Service
public class AdvertGroupServiceWrapper {
    @Autowired
    private AdvertGroupRemoteService service;

    public List<AdvertGroupVO> findByGroupId(Integer adGroupId) throws AlanException {
        ResponseWrapper<AdvertGroupVO> resp = service.findByGroupId(adGroupId);

        if (resp.getCode() != 0) {
            throw new EntityNotFoundException("not found");
        }

        List<AdvertGroupVO> result = new ArrayList<>(0);
        result.add(resp.getData());

        return result;
    }
}
