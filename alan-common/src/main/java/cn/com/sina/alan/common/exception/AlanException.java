package cn.com.sina.alan.common.exception;

import cn.com.sina.alan.common.config.Const;

/**
 * Created by whf on 8/5/16.
 */
public abstract class AlanException extends Exception {
    private int code = Const.ERROR_CODE_SUCCESS;

    protected AlanException() {
        super();
    }

    protected AlanException(String msg) {
        super(msg);
    }

    protected AlanException(int code, String msg) {
        this(msg);

        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
