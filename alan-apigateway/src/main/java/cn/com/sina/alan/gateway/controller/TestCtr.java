package cn.com.sina.alan.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by whf on 8/5/16.
 */
@RestController
public class TestCtr {
    @Value("${config.timeout}")
    private Integer timeout;

    @RequestMapping(value = "/")
    public String home() {
        return timeout.toString();
    }
}
