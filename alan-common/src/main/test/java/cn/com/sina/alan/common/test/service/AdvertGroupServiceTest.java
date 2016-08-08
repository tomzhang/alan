package cn.com.sina.alan.common.test.service;

import cn.com.sina.alan.common.model.AdvertGroupModel;
import cn.com.sina.alan.common.service.AdvertGroupService;
import cn.com.sina.alan.common.test.AlanCommonBaseTestClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * Created by whf on 8/8/16.
 */
public class AdvertGroupServiceTest extends AlanCommonBaseTestClass {
    @Autowired
    private AdvertGroupService adGroupService;

    @Test
    //@Rollback(false)
    public void testCRUD() throws Exception {
        adGroupService.findByPK(1);
        adGroupService.list(new AdvertGroupModel(0, 100));

        AdvertGroupModel toInsert = new AdvertGroupModel();
        toInsert.setGroupName("group name");
        adGroupService.create(toInsert);

        toInsert.setGroupId(1);
        toInsert.setGroupName("new name");
        adGroupService.updateByPK(toInsert);
    }
}
