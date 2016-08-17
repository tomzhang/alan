package cn.com.sina.alan.common.exception;

import cn.com.sina.alan.common.config.Const;

import java.io.IOException;

/**
 * Created by whf on 8/5/16.
 */
public class AlanException extends RuntimeException {
    private int code = Const.ERROR_CODE_SUCCESS;
    private String msg = "";

    public AlanException() {
        super();
    }

    public AlanException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AlanException(int code, String msg) {
        this(msg);

        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return code;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
