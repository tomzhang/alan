package cn.com.sina.alan.gateway.test.service;

import cn.com.sina.alan.gateway.service.SecurityService;
import cn.com.sina.alan.gateway.test.AlanApiGatewayBaseTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by whf on 8/9/16.
 */
public class SecurityServiceTest extends AlanApiGatewayBaseTestClass {
    @Autowired
    private SecurityService securityService;



    @Test
    public void testSign() throws Exception {
        String pubKey = "public_key";
        Map<String, String> parmMap = new HashMap<>();
        parmMap.put("name", "wanghongfei");
        parmMap.put("age", "23");

        boolean result = securityService.checkSign(parmMap, pubKey, "7751a89ccb0c7204cb0133fef57bc88319b10327");
        System.out.println(result);

        Assert.assertEquals(true, result);
    }
}
