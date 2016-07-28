package cn.com.sina.alan;

import feign.Param;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.ReportAsSingleViolation;

/**
 * Created by whf on 7/28/16.
 */
@FeignClient("person")
public interface PersonService {

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public Person findPerson(@RequestParam("name") String name);
}
