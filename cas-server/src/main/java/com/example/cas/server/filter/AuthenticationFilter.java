package com.example.cas.server.filter;


import org.apache.commons.lang.StringUtils;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 登录过滤器
 * @Author yempty
 * @Date 2020/8/27 21:50
 * @Version 1.0
 */
@Component
public class AuthenticationFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private TicketRegistry ticketRegistry;
    /**
     * 校验名单
     */
    private static final String[] includedUrl = {"/cas/api/**"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        if (needCheck(uri)) {
            // check authentication
            String tgc = request.getHeader("TGC");
            String tgt="";
            String userName = null;
            TicketGrantingTicket serviceTicket = (TicketGrantingTicket) ticketRegistry.getTicket(tgt);
            if (serviceTicket != null && !serviceTicket.isExpired()) {
                userName = serviceTicket.getAuthentication().getPrincipal().getId();
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    public static boolean needCheck(String uri) {
        for (String excluded : includedUrl) {
            if (excluded.contains("/**")) {
                excluded = excluded.replace("**", "");
                if (StringUtils.startsWith(uri, excluded)) {
                    return true;
                }
            } else if (excluded.equals(uri)) {
                return true;
            }
        }
        return false;
    }
}
