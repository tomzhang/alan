package cn.com.sina.alan.ms.ea.service.service.api;

import cn.com.sina.alan.common.exception.AlanException;
import cn.com.sina.alan.common.exception.EntityNotFoundException;
import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import cn.com.sina.alan.ms.ea.service.model.AdvertGroupModel;
import cn.com.sina.alan.ms.ea.service.service.AdvertGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by whf on 8/11/16.
 */
@Service
public class AdvertGroupAPIService {
    @Autowired
    private AdvertGroupService advertGroupService;


    /**
     * API接口
     * @param adGroupId
     * @return
     */
    public AdvertGroupVO findByGroupId(Integer adGroupId) throws AlanException {
        AdvertGroupModel adGroupModel = advertGroupService.findByPK(adGroupId);
        if (null == adGroupModel) {
            throw new EntityNotFoundException("广告组" + adGroupId + "不存在");
        }

        AdvertGroupVO vo = new AdvertGroupVO();
        BeanUtils.copyProperties(adGroupModel, vo);

        return vo;
    }
}
