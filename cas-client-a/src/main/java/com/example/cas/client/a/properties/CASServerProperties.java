package com.example.cas.client.a.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description CASServerProperties
 * @Author yempty
 * @Date 2020/8/26 11:06
 */
@ConfigurationProperties(prefix = "cas.server")
public class CASServerProperties {
    private String prefix;
    private String login;
    private String logout;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }
}
