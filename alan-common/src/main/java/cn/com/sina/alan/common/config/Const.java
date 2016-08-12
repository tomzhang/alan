package cn.com.sina.alan.common.config;

/**
 * 常量定义
 * Created by whf on 8/5/16.
 */
public class Const {
    public static final String TEST_SECRET_KEY = "secret_key";

    /**
     * 每页默认大小
     */
    public static final int DEFAULT_PAGE_SIZE = 8;

    public static final int AVERAGE_PARM_LENGTH = 15;

    public static final int ERROR_CODE_SUCCESS = 0;
    public static final String ERROR_CODE_SUCCESS_MSG = "success";

    /**
     * 请求参数名
     */
    public static class RequestParam {
        public static final String SIGN = "sign";
        /* 时间戳 */
        public static final String TIMESTAMP = "ts";
        public static final String PUBLIC_KEY = "pk";
    }

}
