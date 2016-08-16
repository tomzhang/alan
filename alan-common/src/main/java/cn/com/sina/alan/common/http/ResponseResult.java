package cn.com.sina.alan.common.http;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP自定义请求头数据的封装
 * Created by whf on 8/16/16.
 */
public class ResponseResult {
    /**
     * 错误的code值
     */
    private int code;
    /**
     * 错误描述
     */
    private String message;

    private Map<String, String> customHeaders;

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(int code, String message, Map<String, String> customHeaders) {
        this(code, message);

        this.customHeaders = new HashMap<>(customHeaders.size());
        this.customHeaders.putAll(customHeaders);
    }

    /**
     * 添加自定义的键值对
     * @param key
     * @param val
     */
    public void addKeyValuePair(String key, String val) {
        if (null == this.customHeaders) {
            this.customHeaders = new HashMap<>();
        }

        this.customHeaders.put(key, val);
    }

    public Map<String, String> getCustomHeaders() {
        return this.customHeaders;
    }

    public int getCode() {
        return code;
    }

    public String getCodeAsString() {
        return String.valueOf(this.code);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
