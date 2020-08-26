package com.example.cas.client.config;

import com.example.cas.client.properties.CASClientProperties;
import com.example.cas.client.properties.CASServerProperties;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 * @Description CasSecurityConfig
 * @Author yempty
 * @Date 2020/8/26 11:08
 */
@Configuration
public class CasSecurityConfig {
    @Autowired
    private CASClientProperties casClientProperties;
    @Autowired
    private CASServerProperties casServerProperties;
    @Autowired
    private UserDetailsService userDetailService;

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(casClientProperties.getLogin());
        return serviceProperties;
    }

    @Bean
    @Primary
    public AuthenticationEntryPoint authenticationEntryPoint() {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        entryPoint.setLoginUrl(casServerProperties.getLogin());
        entryPoint.setServiceProperties(serviceProperties());
        return entryPoint;
    }

    @Bean
    public TicketValidator ticketValidator() {
        return new Cas20ProxyTicketValidator(casServerProperties.getPrefix());
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(serviceProperties());
        provider.setTicketValidator(ticketValidator());
        provider.setUserDetailsService(userDetailService);
        provider.setKey("client");
        return provider;
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter(AuthenticationProvider authenticationProvider) {
        CasAuthenticationFilter filter = new CasAuthenticationFilter();
        filter.setServiceProperties(serviceProperties());
        filter.setAuthenticationManager(new ProviderManager(authenticationProvider));
        return filter;
    }

    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter sign = new SingleSignOutFilter();
        sign.setIgnoreInitConfiguration(true);
        return sign;
    }
    @Bean
    public LogoutFilter logoutFilter() {
        LogoutFilter filter = new LogoutFilter(casServerProperties.getLogout(), new SecurityContextLogoutHandler());
        filter.setFilterProcessesUrl(casClientProperties.getLogoutRelative());
        return filter;
    }
}
