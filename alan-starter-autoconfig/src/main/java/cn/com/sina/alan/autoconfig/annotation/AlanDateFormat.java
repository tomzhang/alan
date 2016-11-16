package cn.com.sina.alan.autoconfig.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 16/11/2016.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AlanDateFormat {
    String pattern() default "yyyy-MM-dd";
}
