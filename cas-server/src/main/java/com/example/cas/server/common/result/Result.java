package com.example.cas.server.common.result;

import java.io.Serializable;

/**
 * @Description API返回对象
 * @Author yempty
 * @Date 2020/5/14 9:17
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private T data;


    public static <T> Result<T> ok() {
        return restResult(null, ResultCode.OK);
    }

    public static <T> Result<T> ok(T data) {
        return restResult(data, ResultCode.OK);
    }

    public static <T> Result<T> okMsg(String msg) {
        return restResult(null, ResultCode.OK.getCode(), msg);
    }

    public static <T> Result<T> okMsg(T data, String msg) {
        return restResult(data, ResultCode.OK.getCode(), msg);
    }

    public static <T> Result<T> error() {
        return restResult(null, ResultCode.INTERNAL_SERVER_ERROR);
    }

    public static <T> Result<T> error(ResultCode resultCode) {
        return restResult(resultCode);
    }

    public static <T> Result<T> error(String message) {
        return restResult(null, ResultCode.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    private static <T> Result<T> restResult(ResultCode resultCode) {
        return restResult(null, resultCode);
    }

    private static <T> Result<T> restResult(T data, ResultCode resultCode) {
        return restResult(data, resultCode.getCode(), resultCode.getMsg());
    }

    private static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
