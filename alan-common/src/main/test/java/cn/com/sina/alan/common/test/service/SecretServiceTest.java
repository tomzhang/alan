package cn.com.sina.alan.common.test.service;

import cn.com.sina.alan.common.service.SecurityService;
import cn.com.sina.alan.common.test.AlanCommonBaseTestClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by whf on 8/6/16.
 */
public class SecretServiceTest extends AlanCommonBaseTestClass {
    @Autowired
    private SecurityService securityService;

    @Test
    public void testSign() throws Exception {
        String pubKey = "public_key";
        Map<String, String> parmMap = new HashMap<>();
        parmMap.put("name", "wanghongfei");
        parmMap.put("age", "23");

        boolean result = securityService.checkSign(parmMap, pubKey, "abc");
        System.out.println(result);
    }
}
