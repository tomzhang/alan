package cn.com.sina.alan.autoconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 16/11/2016.
 */
@ConfigurationProperties(prefix = "alan.date")
@Getter
@Setter
public class AlanDateProperties {
    /**
     * 日期格式字符串
     */
    private String pattern;
}
