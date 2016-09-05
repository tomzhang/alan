package cn.com.sina.alan.autoconfig.feign;

import feign.RetryableException;
import feign.Retryer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 9/5/16.
 */
public class AlanFeignRetryer implements Retryer {
    private static final Logger log = LoggerFactory.getLogger(AlanFeignRetryer.class);

    @Override
    public void continueOrPropagate(RetryableException e) {
        log.debug("传递异常{}", e.getMessage());

        throw e;
    }

    @Override
    public Retryer clone() {
        return new AlanFeignRetryer();
    }
}
