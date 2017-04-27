package cn.com.sina.alan.autoconfig.monitor;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * InfluxDB配置类
 * Created by wanghongfei on 27/04/2017.
 */
@Configuration
@ConditionalOnProperty(prefix = "alan.auto.monitor", name = "flow", havingValue = "true", matchIfMissing = false)
public class InfluxdbConfig {
    public static final Logger log = LoggerFactory.getLogger(InfluxdbConfig.class);

    @Autowired
    private InfluxdbConnectionProperties properties;

    @Bean
    public InfluxDB influxDB() {
        log.info("连接Influxdb");
        return InfluxDBFactory.connect(properties.getUrl(), properties.getUsername(), properties.getPassword());
    }
}
