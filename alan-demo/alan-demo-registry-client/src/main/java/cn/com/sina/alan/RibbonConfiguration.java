package cn.com.sina.alan;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by whf on 7/30/16.
 */
@Configuration
@RibbonClient(name = "person", configuration = RibbonConfiguration.RibbonConfig.class)
public class RibbonConfiguration {
    static class RibbonConfig {
        @Bean
        public IRule rule() {
            return new WeightedResponseTimeRule();
        }
    }
}
