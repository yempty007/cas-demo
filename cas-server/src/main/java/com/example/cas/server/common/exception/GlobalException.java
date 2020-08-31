package com.example.cas.server.common.exception;


import com.example.cas.server.common.result.ResultCode;

/**
 * @Description 自定义全局异常
 * @Author yempty
 * @Date 2020/5/14 9:16
 */

public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;

    public GlobalException(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }


    public GlobalException(ResultCode resultCode, Throwable e) {
        super(e);
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }


    public GlobalException(String msg) {
        super(msg);
        this.code = ResultCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
    }

    public GlobalException(String msg, Throwable e) {
        super(msg, e);
        this.code = ResultCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
    }

}
