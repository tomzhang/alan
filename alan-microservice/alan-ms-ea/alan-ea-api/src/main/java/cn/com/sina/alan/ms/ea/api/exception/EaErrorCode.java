package cn.com.sina.alan.ms.ea.api.exception;

/**
 * Created by whf on 8/12/16.
 */
public enum EaErrorCode {
    AD_GROUP_NOT_FOUND(10001, "广告组未找到"),
    URL_NOT_FOUND(10002, "URL不存在");

    private int code;
    private String msg;

    private EaErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }

}
