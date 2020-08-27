package com.example.cas.client.b.constant;

/**
 * @Description
 * @Author yempty
 * @Date 2020/8/27 21:57
 * @Version 1.0
 */
public class AppConstant {

    public static final String CAS_TGT_NAME = "tgt_name";

    public static final String LOGIN_TAG = "isLogin";

    public static final String CAS_TICKET_NAME = "ticket";

    public static final String CAS_SERVER_PREFIX = "http://cas.server.com:8080/cas";
    public static final String CAS_SERVER_LOGIN = CAS_SERVER_PREFIX + "/login";
    public static final String CAS_SERVER_LOGOUT = CAS_SERVER_PREFIX + "/logout";

    public static final String CAS_CLIENT_PREFIX = "http://b.casclient.com:8082";
    public static final String CAS_CLIENT_LOGIN = CAS_CLIENT_PREFIX + "/login";
    public static final String CAS_CLIENT_LOGOUT = CAS_CLIENT_PREFIX + "/logout";

}
