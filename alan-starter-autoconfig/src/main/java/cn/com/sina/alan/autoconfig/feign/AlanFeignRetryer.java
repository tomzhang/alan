package cn.com.sina.alan.autoconfig.feign;

import feign.RetryableException;
import feign.Retryer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Feign的重试器
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 9/5/16.
 */
public class AlanFeignRetryer implements Retryer {
    private static final Logger log = LoggerFactory.getLogger(AlanFeignRetryer.class);

    /**
     * 如果将异常抛出, 则Feign就会取消重试
     * @param e
     */
    @Override
    public void continueOrPropagate(RetryableException e) {
        log.debug("重试异常{}", e.getMessage());
        log.warn("连接失败或超时, 不再重试. 连接异常信息: {}", e.getMessage());

        // 抛出异常不再重试
        throw e;
    }

    @Override
    public Retryer clone() {
        return new AlanFeignRetryer();
    }
}
