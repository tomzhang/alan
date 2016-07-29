package cn.com.sina.alan;

import org.springframework.stereotype.Component;

/**
 * PersonService的fallback逻辑
 * Created by whf on 7/29/16.
 */
@Component
public class FeignHystrixFallback implements PersonService {

    @Override
    public Person findPerson(String name) {
        return new Person("fallback method", 18);
    }
}
