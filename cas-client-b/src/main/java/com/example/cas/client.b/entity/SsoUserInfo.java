package com.example.cas.client.b.entity;

/**
 * @Description SsoUserInfo
 * @Author yempty
 * @Date 2020/8/27 13:46
 */
public class SsoUserInfo {

    private Long id;

    private String username;

    public SsoUserInfo(){
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
