package cn.com.sina.alan.common.test;

import cn.com.sina.alan.common.utils.NetUtils;
import org.junit.Test;

/**
 * Created by whf on 8/24/16.
 */
public class NetUtilsTest {
    @Test
    public void testGetIP() {
        String ip = NetUtils.getLocalAddress().getHostAddress();
        System.out.println(ip);
    }
}
