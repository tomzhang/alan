package cn.com.sina.alan.autoconfig.test;

import cn.com.sina.alan.autoconfig.AlanDateProperties;
import cn.com.sina.alan.autoconfig.test.vo.Person;
import cn.com.sina.alan.autoconfig.utils.ObjectUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;


/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 17/11/2016.
 */
public class ObjectUtilsTest {
    private AlanDateProperties alanDateProperties;

    @Before
    public void init() {
        alanDateProperties = new AlanDateProperties();
    }

    @Test
    public void testToMap() {
        Person p = new Person();

        Map<String, Object> map = new ObjectUtils(alanDateProperties).convertToMap(p);
        System.out.println(map);

        String queryString = new ObjectUtils(alanDateProperties).convertToHttpFormParameter(p);
        System.out.println(queryString);
    }

}
