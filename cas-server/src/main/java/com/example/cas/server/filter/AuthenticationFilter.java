package com.example.cas.server.filter;


import cn.hutool.core.util.StrUtil;
import com.example.cas.server.common.exception.GlobalException;
import com.example.cas.server.common.result.ResultCode;
import org.apache.commons.lang.StringUtils;
import org.apereo.cas.CipherExecutor;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

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
    @Qualifier("cookieCipherExecutor")
    private CipherExecutor cipherExecutor;
    @Autowired
    private TicketRegistry ticketRegistry;
    /**
     * 校验名单
     */
    private static final String[] INCLUDED_URL = {"/cas/api/**"};

    /**
     * 管理员账户ID
     */
    private static final Long ROOT_ID = 1L;

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
            boolean hasPermission = false;
            String tgc;
            Cookie cookie = WebUtils.getCookie(request, "TGC");
            if (cookie != null) {
                tgc = cookie.getValue();
            } else {
                tgc = request.getHeader("TGC");
            }
            if(StrUtil.isBlank(tgc)){
                throw new GlobalException(ResultCode.FORBIDDEN);
            }

            // split '@' can get ip,user-agent...
            String tgt = (String) cipherExecutor.decode(tgc, null);
            tgt = StrUtil.subBefore(tgt, "@", false);

            if (StrUtil.isNotBlank(tgt)) {
                TicketGrantingTicket ticket = ticketRegistry.getTicket(tgt, TicketGrantingTicket.class);
                if (ticket != null && !ticket.isExpired()) {
                    Principal principal = ticket.getAuthentication().getPrincipal();
                    Map<String, Object> attributes = principal.getAttributes();
                    LinkedList idList = (LinkedList) attributes.get("id");
                    if (ROOT_ID.toString().equals(idList.get(0).toString())) {
                        hasPermission = true;
                    }
                }
            }

            if (!hasPermission) {
                throw new GlobalException(ResultCode.FORBIDDEN);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    public static boolean needCheck(String uri) {
        for (String excluded : INCLUDED_URL) {
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
