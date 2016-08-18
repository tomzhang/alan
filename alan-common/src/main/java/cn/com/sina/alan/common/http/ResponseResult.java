package cn.com.sina.alan.common.http;

import cn.com.sina.alan.common.config.ErrorCode;

import java.util.Collections;
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

    private static ResponseResult successResult = new ResponseResult(ErrorCode.SUCCESS.code(), ErrorCode.SUCCESS.msg());

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(int code, String message, Map<String, String> customHeaders) {
        this(code, message);

        this.customHeaders = new HashMap<>(customHeaders.size());
        this.customHeaders.putAll(customHeaders);
    }

    public static ResponseResult getSuccessResult() {
        return ResponseResult.successResult;
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
        if (null == this.customHeaders) {
            return Collections.emptyMap();
        }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseResult{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", customHeaders=").append(customHeaders);
        sb.append('}');
        return sb.toString();
    }
}
