package cn.com.sina.alan.ms.ea.service.api;

import cn.com.sina.alan.common.vo.PageResult;
import cn.com.sina.alan.ms.ea.api.exception.AlanEaException;
import cn.com.sina.alan.ms.ea.api.exception.EaErrorCode;
import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import cn.com.sina.alan.ms.ea.service.model.AdvertGroupModel;
import cn.com.sina.alan.ms.ea.service.AdvertGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public AdvertGroupVO findByGroupId(Integer adGroupId) throws AlanEaException {
        AdvertGroupModel adGroupModel = advertGroupService.findByPK(adGroupId);
        if (null == adGroupModel) {
            throw new AlanEaException(EaErrorCode.AD_GROUP_NOT_FOUND.code(), EaErrorCode.AD_GROUP_NOT_FOUND.msg());
        }

        AdvertGroupVO vo = new AdvertGroupVO();
        BeanUtils.copyProperties(adGroupModel, vo);

        return vo;
    }

    public List<AdvertGroupVO> list(AdvertGroupVO vo) {
        AdvertGroupModel example = new AdvertGroupModel();
        BeanUtils.copyProperties(vo, example);

        PageResult<AdvertGroupModel> page = advertGroupService.list(example);

        return page.getData().stream()
                .map( model -> {

                    AdvertGroupVO adVO = new AdvertGroupVO();
                    BeanUtils.copyProperties(model, adVO);
                    return adVO;

                } ).collect(Collectors.toList());
    }
}
