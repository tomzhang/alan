package cn.com.sina.alan.common.vo;

import cn.com.sina.alan.common.config.Const;

/**
 * Created by whf on 8/11/16.
 */
public class ResponseWrapper<T> {
    private int code = Const.ERROR_CODE_SUCCESS;
    private String msg = Const.ERROR_CODE_SUCCESS_MSG;

    private T data;

    public ResponseWrapper() {}

    public ResponseWrapper(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseWrapper(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
