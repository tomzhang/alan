package cn.com.sina.alan.ms.ea.service.service;

import cn.com.sina.alan.common.exception.AlanException;
import cn.com.sina.alan.common.exception.EntityNotFoundException;
import cn.com.sina.alan.common.vo.PageResult;
import cn.com.sina.alan.ms.ea.api.vo.AdvertGroupVO;
import cn.com.sina.alan.ms.ea.service.model.AdvertGroupModel;
import cn.com.sina.alan.ms.ea.service.dao.AdvertGroupModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by whf on 8/8/16.
 */
@Service
public class AdvertGroupService{
    @Autowired
    private AdvertGroupModelMapper adGroupMapper;

    /**
     * API接口
     * @param adGroupId
     * @return
     */
    public AdvertGroupVO findByGroupId(Integer adGroupId) throws AlanException {
        AdvertGroupModel adGroupModel = findByPK(adGroupId);
        if (null == adGroupModel) {
            throw new EntityNotFoundException("广告组" + adGroupId + "不存在");
        }

        AdvertGroupVO vo = new AdvertGroupVO();
        BeanUtils.copyProperties(adGroupModel, vo);

        return vo;
    }

    /**
     * 主键查询
     * @param adGroupId
     * @return
     */
    @Transactional(readOnly = true)
    public AdvertGroupModel findByPK(Integer adGroupId) {
        return adGroupMapper.selectByPrimaryKey(adGroupId);
    }

    /**
     * 条件查询
     * @param example
     * @return
     */
    @Transactional(readOnly = true)
    public PageResult<AdvertGroupModel> list(AdvertGroupModel example) {
        List<AdvertGroupModel> adGroupList = adGroupMapper.findBy(example);
        long tot = adGroupMapper.countFindBy(example);

        return new PageResult<>(tot, adGroupList);
    }

    /**
     * 创建广告组
     * @param example
     */
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void create(AdvertGroupModel example) {
        adGroupMapper.insertSelective(example);
    }

    /**
     * 根据主键更新字段
     * @param example
     * @throws AlanException
     */
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void updateByPK(AdvertGroupModel example) throws AlanException {
        if (null == example.getGroupId()) {
            throw new IllegalArgumentException("advertGroupId不能为空");
        }

        int affected = adGroupMapper.updateByPrimaryKeySelective(example);
        if (affected <= 0) {
            throw new EntityNotFoundException("广告组" + example.getGroupId() + "不存在");
        }
    }
}
