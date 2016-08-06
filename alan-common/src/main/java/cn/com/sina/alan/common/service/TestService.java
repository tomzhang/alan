package cn.com.sina.alan.common.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by whf on 8/6/16.
 */
//@Service
@Component
public class TestService {
    public String sayHello() {
        return "hello";
    }
}
