package com.example.cas.server.dto;

/**
 * @Description UserDTO
 * @Author yempty
 * @Date 2020/8/31 15:16
 */
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private Integer locked;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }
}
