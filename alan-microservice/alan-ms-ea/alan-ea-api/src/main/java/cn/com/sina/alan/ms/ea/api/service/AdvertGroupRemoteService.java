package cn.com.sina.alan.ms.ea.api.service;

import cn.com.sina.alan.common.config.Const;
import cn.com.sina.alan.ms.ea.api.exception.AlanEaException;
import cn.com.sina.alan.ms.ea.api.exception.EaErrorCode;
import cn.com.sina.alan.ms.ea.api.service.feign.AdvertGroupFeignClient;
import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import cn.com.sina.alan.common.vo.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Service;


/**
 * Created by whf on 8/11/16.
 */
@ConditionalOnMissingClass("cn.com.sina.alan.ms.ea.AlanMsEaApplication")
@Service
public class AdvertGroupRemoteService {
    @Autowired
    private AdvertGroupFeignClient service;

    public AdvertGroupVO findByGroupId(Integer adGroupId) throws AlanEaException {
        ResponseWrapper<AdvertGroupVO> resp = service.findByGroupId(adGroupId);

        if (resp.getCode() != Const.ERROR_CODE_SUCCESS) {
            throw new AlanEaException(EaErrorCode.AD_GROUP_NOT_FOUND);
        }

        return resp.getData();
    }
}
