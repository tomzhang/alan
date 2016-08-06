package cn.com.sina.alan;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by whf on 8/6/16.
 */
@Service
public class PersonService {
    @Autowired
    private PersonRemoteService remoteService;

    @HystrixCommand(fallbackMethod = "findPersonFallback")
    public Person findPerson(String name) {
        return remoteService.findPerson(name);
    }

    private Person findPersonFallback(String name) {
        return new Person("hystrix command fall back", 10);
    }
}
