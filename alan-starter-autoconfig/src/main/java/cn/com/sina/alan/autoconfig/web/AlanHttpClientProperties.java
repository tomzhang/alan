package cn.com.sina.alan.autoconfig.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 9/5/16.
 */
@Component
@ConfigurationProperties(prefix = "alan.config.httpClient")
public class AlanHttpClientProperties {
    /**
     * 等待响应的最长时间, 秒
     */
    private Integer readTimeout = 5;
    /**
     * 连接超时时间, 秒
     */
    private Integer connectionTimeout = 5;

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
