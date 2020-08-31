package com.example.cas.server.common.result;

/**
 * @Description 系统错误编码
 * @Author yempty
 * @Date 2020/5/14 9:15
 */
public enum ResultCode {

    /**
     * 错误编码
     */
    OK(0, "正常"),
    UNAUTHORIZED(401, "拒绝访问，沒有登录"),
    FORBIDDEN(403, "拒绝访问，没有权限"),
    INTERNAL_SERVER_ERROR(500, "其他未知错误"),
    INVALID_OPERATION(4, "无效操作"),
    INVALID_JSON_FORMAT(7, "JSON 格式无效"),
    INVALID_JSON_CONTENT(8, "JSON 内容无效"),
    ACCOUNT_PASSWORD_ERROR(10001, "用户名或密码错误"),
    ACCOUNT_LOCK(10002, "账号已被锁定"),
    CAPTCHA_ERROR(10003, "验证码不正确"),
    PARAMS_GET_ERROR(10004, "获取参数失败"),
    TOKEN_NOT_EMPTY(10005, "token不能为空"),
    TOKEN_INVALID(10006, "token失效，请重新登录"),
    NAME_ERROR(10007, "此名称已被使用"),
    UPLOAD_FILE_EMPTY(10008, "上传文件不能为空"),
    UPLOAD_FILE_ERROR(10009, "上传文件发生错误"),
    REDIS_ERROR(10010, "缓存服务异常");

    private Integer code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static String getMsg(Integer code) {
        ResultCode[] resultCodes = values();
        for (ResultCode resultCode : resultCodes) {
            if (resultCode.getCode().equals(code)) {
                return resultCode.getMsg();
            }
        }
        return null;
    }
}

