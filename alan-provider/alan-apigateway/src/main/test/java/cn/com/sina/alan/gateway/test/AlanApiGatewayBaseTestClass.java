package cn.com.sina.alan.gateway.test;

import cn.com.sina.alan.gateway.ApiGatewayApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by whf on 8/9/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApiGatewayApplication.class)
@Transactional
public class AlanApiGatewayBaseTestClass {
}
