package cn.com.sina.alan.common.exception;

/**
 * Created by whf on 8/5/16.
 */
public abstract class AlanException extends Exception {
    protected AlanException() {
        super();
    }

    protected AlanException(String msg) {
        super(msg);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
