package com.example.cas.client.b.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.cas.client.b.constant.AppConstant;
import com.example.cas.client.b.entity.SsoUserInfo;
import com.example.cas.client.b.util.CasRequestUtils;
import com.example.cas.client.b.util.SessionMappingStorage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPathConstants;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Description 测试控制器
 * @Author yempty
 * @Date 2020/8/27 17:22
 */
@RestController
public class LoginController {
    private static final Log log = LogFactory.get();

    private SessionMappingStorage sessionMappingStorage = new SessionMappingStorage();

    @GetMapping("hello")
    public String hello() {
        return "hello world!" + LocalDateTime.now();
    }

    @GetMapping("user")
    public String hello(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SsoUserInfo ssoUserInfo = (SsoUserInfo) session.getAttribute("ssoUserInfo");
        return JSONUtil.toJsonStr(ssoUserInfo);
    }

    @GetMapping("login")
    public String login(HttpServletRequest request) {
        SsoUserInfo ssoUserInfo = new SsoUserInfo(9999L, "admin");
        request.setAttribute("ssoUserInfo", ssoUserInfo);

        log.info("login success!!!!");
        return "login success!!!!";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        this.sessionMappingStorage.removeBySessionById(session.getId());
        session.invalidate();

        try {
            String redirect = StrUtil.format("{}?service={}/hello",
                    AppConstant.CAS_SERVER_LOGOUT,
                    AppConstant.CAS_CLIENT_PREFIX);
            response.sendRedirect(redirect);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("logout success!!!!");
        return "logout success~~~~~";
    }

    @GetMapping("/login/cas")
    public void login(String ticket, HttpServletRequest request, HttpServletResponse response) {
        log.info("receive cas login request");
        HttpSession session = request.getSession();
        try {
            this.sessionMappingStorage.removeBySessionById(session.getId());
        } catch (Exception exception) {
        }
        this.sessionMappingStorage.addSessionById(ticket, session);

        SsoUserInfo ssoUserInfo = null;
        try {
            ssoUserInfo = CasRequestUtils.checkStTicket(ticket);
            session.setAttribute("ssoUserInfo", ssoUserInfo);

            response.sendRedirect(AppConstant.CAS_CLIENT_PREFIX);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @PostMapping("logout/cas")
    public void logout(HttpServletRequest request) {
        log.info("receive cas logout request");
        String logoutRequest = request.getParameter("logoutRequest");
        log.info("logoutRequest:[{}]", logoutRequest);
        XmlUtil.setNamespaceAware(false);
        Document document = XmlUtil.readXML(logoutRequest);
        String token = (String) XmlUtil.getByXPath("/LogoutRequest/SessionIndex", document, XPathConstants.STRING);

        if (StrUtil.isNotBlank(token)) {
            HttpSession session = this.sessionMappingStorage.removeSessionByMappingId(token);
            if (session != null) {
                String sessionID = session.getId();
                log.debug("Invalidating session [{}] for token [{}]", sessionID, token);

                try {
                    session.invalidate();
                    request.logout();
                } catch (IllegalStateException exception) {
                    log.debug("Error invalidating session.", exception);
                } catch (ServletException exception) {
                    log.debug("Error performing request.logout.");
                }
            }
        }

    }
}
