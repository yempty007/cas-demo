package com.example.cas.server.entity;

/**
 * @Description ReturnMessage
 * @Author yempty
 * @Date 2020/8/26 13:26
 */
public class ReturnMessage {
    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
