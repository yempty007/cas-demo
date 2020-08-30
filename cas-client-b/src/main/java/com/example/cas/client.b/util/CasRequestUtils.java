package com.example.cas.client.b.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.cas.client.b.constant.AppConstant;
import com.example.cas.client.b.entity.SsoUserInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathConstants;
import java.util.HashMap;

/**
 * @Description cas rest 请求工具
 * @Author yempty
 * @Date 2020/8/27 10:31
 */
public class CasRequestUtils {

    private static final Log log = LogFactory.get();

    /**
     * 获取TGT票据
     * location如：http://cas.server.com:8080/cas/v1/tickets/TGT-3-JXHdcIKq8KpZwNriSut3G1o-SdaFvWV-6L4rGHMkkbaUOSFLjmPMqgdww0ykTRSh9eEMacBook-Pro
     * 最后一串字符为 TGT
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static String getTgtTicket(String username, String password) {
        HashMap<String, Object> params = new HashMap<>(MapUtil.DEFAULT_INITIAL_CAPACITY);
        params.put("username", username);
        params.put("password", password);
        HttpResponse response = HttpUtil.createPost(AppConstant.CAS_CLIENT_PREFIX + "/v1/tickets").form(params).execute();
        String location = response.header(Header.LOCATION);
        log.info("location:[{}]", location);
        return location;
    }

    /**
     * 用TGT申请ST票据
     *
     * @param url TGT票据地址
     * @return
     */
    public static String getStTicket(String url) {
        HashMap<String, Object> params = new HashMap<>(MapUtil.DEFAULT_INITIAL_CAPACITY);
        params.put("service", AppConstant.CAS_CLIENT_PREFIX);
        String result = HttpUtil.post(url, params);
        log.info("StTicket:[{}]", result);
        return result;
    }

    /**
     * 票据验证
     *
     * @param stTicket ST票据
     * @return cas 用户对象
     * @throws Exception
     */
    public static SsoUserInfo checkStTicket(String stTicket) throws Exception {
        HashMap<String, Object> params = new HashMap<>(MapUtil.DEFAULT_INITIAL_CAPACITY);
        params.put("service", AppConstant.CAS_CLIENT_LOGIN);
        params.put("ticket", stTicket);
        String body = HttpUtil.get(AppConstant.CAS_SERVER_PREFIX + "/p3/serviceValidate", params);
        System.out.println(body);

        XmlUtil.setNamespaceAware(false);
        Document document = XmlUtil.readXML(body);
        String error = (String) XmlUtil.getByXPath("/serviceResponse/authenticationFailure", document, XPathConstants.STRING);
        if (StrUtil.isNotBlank(error)) {
            throw new Exception("TicketValidationException");
        } else {
            String principal = (String) XmlUtil.getByXPath("/serviceResponse/authenticationSuccess/user", document, XPathConstants.STRING);
            if (StrUtil.isBlank(principal)) {
                throw new Exception("No principal was found in the response from the CAS server.");
            } else {
                String username = (String) XmlUtil.getByXPath("/serviceResponse/authenticationSuccess/attributes/username", document, XPathConstants.STRING);
                String idString = (String) XmlUtil.getByXPath("/serviceResponse/authenticationSuccess/attributes/id", document, XPathConstants.STRING);

                return new SsoUserInfo(Long.parseLong(idString), username);
            }
        }
    }

    /**
     * 销毁TGT票据
     *
     * @param tgtTicket TGT票据
     */
    public static void destroyTgtTicket(String tgtTicket) {
        HttpUtil.createRequest(Method.DELETE, AppConstant.CAS_CLIENT_PREFIX + "/v1/tickets" + tgtTicket).execute();
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

        Node authenticationSuccess = (Node) XmlUtil.getByXPath("/serviceResponse/authenticationSuccess", document, XPathConstants.NODE);
        log.debug("authenticationSuccess:[{}]", authenticationSuccess);

        Node authenticationFailure = (Node) XmlUtil.getByXPath("/serviceResponse/authenticationFailure", document, XPathConstants.NODE);
        log.debug("authenticationFailure:[{}]", authenticationFailure);

        Object user = XmlUtil.getByXPath("/serviceResponse/authenticationSuccess/user", document, XPathConstants.STRING);
        log.debug("user:[{}]", user);

        Object attributesNode = XmlUtil.getByXPath("/serviceResponse/authenticationSuccess/attributes", document, XPathConstants.NODE);
        log.debug("attributesNode:[{}]", attributesNode);

    }
}
