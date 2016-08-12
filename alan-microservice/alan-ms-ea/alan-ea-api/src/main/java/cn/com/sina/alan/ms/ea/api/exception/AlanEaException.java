package cn.com.sina.alan.ms.ea.api.exception;

import cn.com.sina.alan.common.exception.AlanException;

/**
 * Created by whf on 8/12/16.
 */
public class AlanEaException extends AlanException {

    public AlanEaException() {
    }

    public AlanEaException(String msg) {
        super(msg);
    }

    public AlanEaException(EaErrorCode errorCode) {
        super(errorCode.code(), errorCode.msg());
    }

    public AlanEaException(int code, String msg) {
        super(code, msg);
    }

}
