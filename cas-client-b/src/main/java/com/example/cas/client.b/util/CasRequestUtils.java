package com.example.cas.client.b.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import com.example.cas.client.b.entity.SsoUserInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

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

    public static String getStTicket(String url) {
        HashMap<String, Object> params = new HashMap<>(MapUtil.DEFAULT_INITIAL_CAPACITY);
        params.put("service", CLIENT_SERVER_URL);
        String body = HttpUtil.post(url, params);
        System.out.println(body);
        return body;
    }

    public static SsoUserInfo checkStTicket(String stTicket) throws Exception {
        HashMap<String, Object> params = new HashMap<>(MapUtil.DEFAULT_INITIAL_CAPACITY);
        params.put("service", CLIENT_SERVER_URL);
        params.put("ticket", stTicket);
        String body = HttpUtil.get(CAS_SERVER_URL + "/p3/serviceValidate", params);
        System.out.println(body);

        Document document = XmlUtil.readXML(body);
        String error = XmlUtil.elementText(document.getDocumentElement(), "authenticationFailure");
        if (StrUtil.isNotBlank(error)) {
            throw new Exception("TicketValidationException");
        } else {
            String principal = XmlUtil.elementText(document.getDocumentElement(), "user");
            if (StrUtil.isBlank(principal)) {
                throw new Exception("No principal was found in the response from the CAS server.");
            } else {
                // XmlUtil.xmlToMap()
            }
        }
        SsoUserInfo ssoUserInfo = JSONUtil.toBean(body, SsoUserInfo.class);
        return ssoUserInfo;
    }

    public static void destroyTgtTicket(String tgtTicket) {
        HttpUtil.createRequest(Method.DELETE, CAS_SERVER_URL + "/v1/tickets" + tgtTicket).execute();
    }

    public static void main(String[] args) {
        // String location = getTgtTicket("yempty", "123456");
        // String stTicket = getStTicket(location);
        // String stTicket = "ST-18-UGQk-WaV20DhMh5u0Iyc9-I0ubsASUS-001";
        // SsoUserInfo ssoUserInfo = checkStTicket(stTicket);

        String body = "<cas:serviceResponse xmlns:cas='http://cas.server.com:8080/cas'>\n" +
                "\t<cas:authenticationSuccess>\n" +
                "\t\t<cas:user>yempty</cas:user>\n" +
                "\t\t<cas:attributes>\n" +
                "\t\t\t<cas:credentialType>UsernamePasswordCredential</cas:credentialType>\n" +
                "\t\t\t<cas:isFromNewLogin>true</cas:isFromNewLogin>\n" +
                "\t\t\t<cas:authenticationDate>2020-08-27T23:12:37.348+08:00[Asia/Shanghai]</cas:authenticationDate>\n" +
                "\t\t\t<cas:authenticationMethod>QueryDatabaseAuthenticationHandler</cas:authenticationMethod>\n" +
                "\t\t\t<cas:successfulAuthenticationHandlers>QueryDatabaseAuthenticationHandler\n" +
                "\t\t\t</cas:successfulAuthenticationHandlers>\n" +
                "\t\t\t<cas:longTermAuthenticationRequestTokenUsed>false</cas:longTermAuthenticationRequestTokenUsed>\n" +
                "\t\t\t<cas:id>1</cas:id>\n" +
                "\t\t\t<cas:username>yempty</cas:username>\n" +
                "\t\t</cas:attributes>\n" +
                "\t</cas:authenticationSuccess>\n" +
                "</cas:serviceResponse>";

        XmlUtil.setNamespaceAware(false);
        Document document = XmlUtil.parseXml(body);
        Element documentElement = document.getDocumentElement();

        Map<String, Object> bodyMap = XmlUtil.xmlToMap(body);
        if (bodyMap.containsKey("cas:authenticationFailure")) {
            System.out.println("authenticationFailure");
        } else if (bodyMap.containsKey("cas:authenticationSuccess")) {
            Map<String, Object> successMap = (Map<String, Object>) bodyMap.get("cas:authenticationSuccess");
            if (!successMap.containsKey("cas:user") || StrUtil.isBlank(successMap.get("cas:user").toString())) {
                System.out.println("no principal");
            } else {

            }
        } else {
            System.out.println("error");
        }

    }
}
