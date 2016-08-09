package cn.com.sina.alan.gateway.config;

/**
 * Created by whf on 8/6/16.
 */
public enum ApiKeyStatus {
    ENABLE(0, "启用"),
    DISABLE(1, "禁用");

    private int code;
    private String msg;

    private ApiKeyStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    public static boolean isEnable(int code) {
        return code == ENABLE.code;
    }

    public static ApiKeyStatus fromCode(Integer code) {
        if (null == code) {
            return null;
        }

        switch (code.intValue()) {
            case 0:
                return ENABLE;

            case 1:
                return DISABLE;
        }

        return null;
    }
}
