package com.example.cas.client.b.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.cas.client.b.constant.AppConstant;
import com.example.cas.client.b.entity.SsoUserInfo;
import com.example.cas.client.b.util.CasRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Description TestController
 * @Author yempty
 * @Date 2020/8/27 17:22
 */
@RestController
public class LoginController {
    private static final Log log = LogFactory.get();

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
    public String login(HttpServletRequest request, HttpServletResponse response) {
        SsoUserInfo ssoUserInfo = new SsoUserInfo(9999L, "admin");
        request.setAttribute("ssoUserInfo", ssoUserInfo);
        // 将登录标识存入cookie
        Cookie cookie = new Cookie(AppConstant.LOGIN_TAG, "true");
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);
        log.info("login success!!!!");
        return "login success!!!!";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        // 清空session cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie != null && AppConstant.LOGIN_TAG.equals(cookie.getName())) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
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
        SsoUserInfo ssoUserInfo = null;
        try {
            ssoUserInfo = CasRequestUtils.checkStTicket(ticket);
            HttpSession session = request.getSession();
            session.setAttribute("ssoUserInfo", ssoUserInfo);
            response.sendRedirect(AppConstant.CAS_CLIENT_PREFIX);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @PostMapping("logout/cas")
    public void logout(HttpServletRequest request) {
        log.info("receive cas logout request");
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
