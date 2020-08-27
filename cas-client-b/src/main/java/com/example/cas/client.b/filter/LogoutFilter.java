package com.example.cas.client.b.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.cas.client.b.constant.AppConstant;
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
public class LogoutFilter implements Filter {

    private static final Log log = LogFactory.get();

    private  String redirectUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         redirectUrl = StrUtil.format("{}?service={}",
                AppConstant.CAS_SERVER_LOGIN, AppConstant.CAS_CLIENT_LOGIN);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie cookie = WebUtils.getCookie(request, AppConstant.LOGIN_TAG);
        log.info("LOGIN_TAG cookie value:{}", cookie == null ? StrUtil.EMPTY : cookie.getValue());

        if (cookie == null || StrUtil.isBlank(cookie.getValue())) {
            // String redirectUrl = StrUtil.format("{}?service={}{}",
            //         AppConstant.CAS_SERVER_LOGIN, AppConstant.CAS_CLIENT_PREFIX, request.getRequestURI());
            log.info("RequestURI:{}", request.getRequestURI());
            log.info("RedirectUrl:{}", redirectUrl);
            response.sendRedirect(redirectUrl);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
