package cn.com.sina.alan.autoconfig.test.vo;

import freemarker.ext.beans.HashAdapter;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.map.HashedMap;

import java.util.*;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 17/11/2016.
 */
@Getter
@Setter
public class Person {
    private Address addr = new Address();
    private String name = "whf";

    private Date date = new Date();

    private List<String> list = Arrays.asList("a", "b", "c");
    private Map<String, String> map;

    public Person() {
        map = new HashMap<>();
        map.put("age", "18");
        map.put("age1", "18");
        map.put("age2", "18");
        map.put("age3", "18");
    }
}
