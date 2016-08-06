package cn.com.sina.alan.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by whf on 8/6/16.
 */
@Configuration
@MapperScan("cn.com.sina.alan.common.dao")
public class MybatisConfig {
}
