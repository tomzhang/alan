package cn.com.sina.alan.common.exception;

/**
 * Created by whf on 8/27/16.
 */
public class AlanConcurrentException extends Exception {
    private Exception reason;

    public AlanConcurrentException(Exception reason) {
        this.reason = reason;
    }

    public Exception getReason() {
        return this.reason;
    }
}
