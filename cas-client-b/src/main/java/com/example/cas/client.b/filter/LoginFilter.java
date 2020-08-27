package com.example.cas.client.b.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.cas.client.b.constant.AppConstant;
import com.example.cas.client.b.entity.SsoUserInfo;
import com.example.cas.client.b.util.CasRequestUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @Author yempty
 * @Date 2020/8/27 21:50
 * @Version 1.0
 */
@Component
public class LoginFilter implements Filter {

    private static final Log log = LogFactory.get();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie cookie = WebUtils.getCookie(request, AppConstant.LOGIN_TAG);
        log.info("LOGIN_TAG cookie value:{}", cookie == null ? StrUtil.EMPTY : cookie.getValue());

        if (cookie == null || StrUtil.isBlank(cookie.getValue())) {
            // 如果是cas server回调，有ticket值，根据ticket获取用户信息
            String ticket = request.getParameter(AppConstant.CAS_TICKET_NAME);
            if (StrUtil.isNotBlank(ticket)) {
                try {
                    SsoUserInfo ssoUserInfo = CasRequestUtils.checkStTicket(ticket);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            String redirectUrl = StrUtil.format("{}?service={}{}",
                    AppConstant.CAS_SERVER_LOGIN, AppConstant.CAS_CLIENT_PREFIX, request.getRequestURI());
            log.info("RequestURI:{}", request.getRequestURI());
            log.info("RedirectUrl:{}", redirectUrl);
            response.sendRedirect(redirectUrl);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
