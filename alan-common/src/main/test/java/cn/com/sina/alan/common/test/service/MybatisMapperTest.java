package cn.com.sina.alan.common.test.service;

import cn.com.sina.alan.common.dao.AdvertGroupModelMapper;
import cn.com.sina.alan.common.service.TestService;
import cn.com.sina.alan.common.test.AlanCommonBaseTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by whf on 8/6/16.
 */
public class MybatisMapperTest extends AlanCommonBaseTestClass {
    @Autowired
    private TestService testService;

    @Autowired
    private AdvertGroupModelMapper advertGroupMapper;

    @Test
    public void testHello() {
        Assert.assertNotNull(testService);
        Assert.assertNotNull(advertGroupMapper);

        advertGroupMapper.selectByPrimaryKey(1);
    }
}
