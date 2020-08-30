package com.example.cas.client.b.constant;

/**
 * @Description 常量类
 * @Author yempty
 * @Date 2020/8/27 21:57
 * @Version 1.0
 */
public class AppConstant {

    /**
     * cas 服务前缀
     */
    public static final String CAS_SERVER_PREFIX = "http://cas.server.com:8080/cas";
    /**
     * cas 服务登录地址
     */
    public static final String CAS_SERVER_LOGIN = CAS_SERVER_PREFIX + "/login";
    /**
     * cas 服务登出地址
     */
    public static final String CAS_SERVER_LOGOUT = CAS_SERVER_PREFIX + "/logout";

    /**
     * 客户端前缀
     */
    public static final String CAS_CLIENT_PREFIX = "http://b.casclient.com:8082";
    /**
     * 客户端接收 cas 服务 ticket 地址
     */
    public static final String CAS_CLIENT_LOGIN = CAS_CLIENT_PREFIX + "/login/cas";
    /**
     * 客户端接收 cas 服务 登出地址
     */
    public static final String CAS_CLIENT_LOGOUT = CAS_CLIENT_PREFIX + "/logout/cas";

}
