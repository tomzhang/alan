package cn.com.sina.alan.autoconfig.test;

import cn.com.sina.alan.autoconfig.test.vo.Person;
import cn.com.sina.alan.autoconfig.utils.ObjectUtils;
import org.junit.Test;

import java.util.Map;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 17/11/2016.
 */
public class ObjectUtilsTest {
    @Test
    public void testToMap() {
        Person p = new Person();
        Map<String, Object> map = ObjectUtils.convertToMap(p);

        System.out.println(map);
    }
}
