package cn.com.sina.alan.ms.ea.api.vo;

/**
 * Created by whf on 8/11/16.
 */
public class ResponseWrapper {
    private int code = 0;
    private Object data;

    public ResponseWrapper() {}

    public ResponseWrapper(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
