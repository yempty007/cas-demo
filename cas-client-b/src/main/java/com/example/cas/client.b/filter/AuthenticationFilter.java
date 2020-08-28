package com.example.cas.client.b.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.cas.client.b.constant.AppConstant;
import com.example.cas.client.b.entity.SsoUserInfo;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description
 * @Author yempty
 * @Date 2020/8/27 21:50
 * @Version 1.0
 */
@Component
public class AuthenticationFilter implements Filter {

    private static final Log log = LogFactory.get();

    private static final String[] excludedUrl = {"/login/**", "/logout/**", "/hello"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("AuthenticationFilter -come");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        log.info("sessionId", session.getId());
        String uri = request.getRequestURI();
        log.info("RequestURI:{}", uri);
        if (needCheck(uri)) {
            log.info("into check user");
            SsoUserInfo ssoUserInfo = (SsoUserInfo) session.getAttribute("ssoUserInfo");

            if (ssoUserInfo == null) {
                String redirectUrl = StrUtil.format("{}?service={}",
                        AppConstant.CAS_SERVER_LOGIN, URLUtil.encodeAll(AppConstant.CAS_CLIENT_LOGIN));
                log.info("RedirectUrl:{}", redirectUrl);
                response.sendRedirect(redirectUrl);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public static boolean needCheck(String uri) {
        for (String excluded : excludedUrl) {
            if (excluded.contains("/**")) {
                excluded = excluded.replace("/**", "");
                if (StrUtil.startWith(uri, excluded)) {
                    return false;
                }
            } else if (excluded.equals(uri)) {
                return false;
            }
        }
        return true;
    }
}
