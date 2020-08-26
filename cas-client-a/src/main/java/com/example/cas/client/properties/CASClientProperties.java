package com.example.cas.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description CASClientProperties
 * @Author yempty
 * @Date 2020/8/26 11:07
 */
@ConfigurationProperties(prefix = "cas.client")
public class CASClientProperties {
    private String prefix;
    private String login;
    private String logoutRelative;
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

    public String getLogoutRelative() {
        return logoutRelative;
    }

    public void setLogoutRelative(String logoutRelative) {
        this.logoutRelative = logoutRelative;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }
}
