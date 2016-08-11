package cn.com.sina.alan.ms.ea.api.vo;

/**
 * Created by whf on 8/11/16.
 */
public class ResponseWrapper<T> {
    private int code = 0;
    private T data;

    public ResponseWrapper() {}

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
}
