package cn.com.sina.alan.autoconfig.test.vo;

import cn.com.sina.alan.common.config.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 17/11/2016.
 */
@Getter
@Setter
public class Person {
    private int[] ages = {
            1, 2, 3, 4
    };

    private String[] killers = new String[] {"k1", "k2", "k3"};

    private Address addr = new Address();
    private String name = "whf";

    private Date date = new Date();

    private List<String> list = Arrays.asList("a", "b", "c");
    private Map<String, String> map;
    ErrorCode code = ErrorCode.FAILED;


    public Person() {
        map = new HashMap<>();
        map.put("age", "18");
        map.put("age1", "18");
        map.put("age2", "18");
        map.put("age3", "18");
    }
}
