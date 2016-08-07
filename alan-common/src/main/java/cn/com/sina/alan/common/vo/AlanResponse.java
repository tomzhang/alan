package cn.com.sina.alan.common.vo;

import cn.com.sina.alan.common.config.ErrorCode;

/**
 * Created by whf on 8/7/16.
 */
public class AlanResponse {
    /**
     * 错误码
     */
    private int code = ErrorCode.SUCCESS.code();
    /**
     * 错误描述
     */
    private String msg = ErrorCode.SUCCESS.msg();

    /**
     * 数据字段
     */
    private Object data;


    public static AlanResponse successResp = new AlanResponse();
    public static AlanResponse failedResp = new AlanResponse(ErrorCode.FAILED);

    /**
     * 构造结果为成功, 不带数据的返回值对象
     */
    public AlanResponse() {
    }

    /**
     * 构造结果为成功 带数据的返回对象
     * @param data
     */
    public AlanResponse(Object data) {
        this.data = data;
    }

    /**
     * 指定错误码和错误信息, 不带数据
     * @param ec
     */
    public AlanResponse(ErrorCode ec) {
        this.code = ec.code();
        this.msg = ec.msg();
    }

    /**
     * 结果为失败, 指定错误信息
     * @param msg
     */
    public AlanResponse(String msg) {
        this.code = ErrorCode.FAILED.code();
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
