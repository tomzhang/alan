package cn.com.sina.alan.autoconfig.monitor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by wanghongfei on 27/04/2017.
 */
@Component
@ConfigurationProperties(prefix = "alan.influxdb")
public class InfluxdbConnectionProperties {
    /**
     * HTTP REST接口地址
     */
    private String url = "http://127.0.0.1:8086";
    /**
     * 用户名
     */
    private String username = "root";
    /**
     * 密码
     */
    private String password = "root";
    /**
     * 默认数据库
     */
    private String database = "flow";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
