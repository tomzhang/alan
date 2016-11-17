package cn.com.sina.alan.autoconfig.test.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 17/11/2016.
 */
@Getter
@Setter
public class Address {
    private String name = "earth";
    private Integer number = 12;

    private List<String> list = Arrays.asList("a", "b", "c");

}
