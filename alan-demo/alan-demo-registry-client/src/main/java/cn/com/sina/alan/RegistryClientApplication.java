package cn.com.sina.alan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
@RestController
public class RegistryClientApplication {
    @Autowired
    private PersonService personService;


    @RequestMapping(value = "/person", method = RequestMethod.GET, produces = "application/json")
    public Person caller(@RequestParam String name) {
        return personService.findPerson(name);
    }


    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public Person findPerson(@RequestParam String name) {
        return new Person(name, 22);
    }


	public static void main(String[] args) {
		SpringApplication.run(RegistryClientApplication.class, args);
	}
}
