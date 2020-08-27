package com.example.cas.client.b.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.*;
import cn.hutool.json.JSONUtil;
import com.example.cas.client.b.entity.SsoUserInfo;

import java.util.HashMap;

/**
 * @Description CasRequestUtils
 * @Author yempty
 * @Date 2020/8/27 10:31
 */
public class CasRequestUtils {

    private static final String CAS_SERVER_URL = "http://cas.server.com:8080/cas/";
    private static final String CLIENT_SERVER_URL = "http://a.casclient.com:8081/";

    public static String getTgtTicket(String username, String password) {
        HashMap<String, Object> params = new HashMap<>(MapUtil.DEFAULT_INITIAL_CAPACITY);
        params.put("username", username);
        params.put("password", password);
        HttpResponse response = HttpUtil.createPost(CAS_SERVER_URL + "/v1/tickets").form(params).execute();
        String location = response.header(Header.LOCATION);
        System.out.println(location);
        return location;
    }

    public static String getStTicket(String url){
        HashMap<String, Object> params = new HashMap<>(MapUtil.DEFAULT_INITIAL_CAPACITY);
        params.put("service", CLIENT_SERVER_URL);
        String body = HttpUtil.post(url, params);
        System.out.println(body);
        return body;
    }

    public static SsoUserInfo checkStTicket(String stTicket) {
        HashMap<String, Object> params = new HashMap<>(MapUtil.DEFAULT_INITIAL_CAPACITY);
        params.put("service", CLIENT_SERVER_URL);
        params.put("ticket", stTicket);
        String body = HttpUtil.get(CAS_SERVER_URL + "/p3/serviceValidate", params);
        System.out.println(body);
        SsoUserInfo ssoUserInfo = JSONUtil.toBean(body, SsoUserInfo.class);
        return ssoUserInfo;
    }

    public static void destroyTgtTicket(String tgtTicket){
        HttpUtil.createRequest(Method.DELETE, CAS_SERVER_URL +"/v1/tickets"+tgtTicket).execute();
    }

    public static void main(String[] args) {
        // String location = getTgtTicket("yempty", "123456");
        // String stTicket = getStTicket(location);
        String stTicket = "ST-18-UGQk-WaV20DhMh5u0Iyc9-I0ubsASUS-001";
        SsoUserInfo ssoUserInfo = checkStTicket(stTicket);
    }
}
