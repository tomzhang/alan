package cn.com.sina.alan.gateway.test.service;

import cn.com.sina.alan.gateway.controller.TestCtr;
import cn.com.sina.alan.gateway.test.AlanApiGatewayBaseTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by whf on 8/9/16.
 */
public class SecurityServiceTest extends AlanApiGatewayBaseTestClass {
    @Autowired
    TestCtr testCtr;

    @Test
    public void testConfig() {
        Assert.assertNotNull(testCtr);
    }
}
