package com.example.cas.client.b.entity;

/**
 * @Description 单点用户信息
 * @Author yempty
 * @Date 2020/8/27 13:46
 */
public class SsoUserInfo {

    /**
     * cas 系统 用户ID
     */
    private Long id;

    /**
     * cas 系统 用户名
     */
    private String username;

    public SsoUserInfo() {
    }

    public SsoUserInfo(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
