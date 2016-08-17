package cn.com.sina.alan.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by whf on 8/17/16.
 */
@Configuration
public class AlanWebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private AlanWebInterceptor alanWebInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(alanWebInterceptor).addPathPatterns("/**");
    }
}
