package cn.com.sina.alan.autoconfig.test.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 17/11/2016.
 */
@Getter
@Setter
public class Address {
    private String name = "earth";
    private Integer number = 12;

    private Map<String, String> map;

    private List<String> list = Arrays.asList("a", "b", "c");

    public Address() {
        map = new HashMap<>();
        map.put("key1", "val1");
    }

}
