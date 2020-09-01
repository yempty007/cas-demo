package com.example.cas.server.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Description User
 * @Author yempty
 * @Date 2020/8/31 15:16
 */
@Entity
@DynamicUpdate
@Table(name = "cas_user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;
    @Column
    private String password;
    @Column
    private Integer locked;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    public User() {
    }

    public User(Long id,String username,Integer locked,LocalDateTime createTime) {
        this.id=id;
        this.username=username;
        this.locked=locked;
        this.createTime=createTime;
    }

    public User(Long id,String username,String password,Integer locked,LocalDateTime createTime) {
        this.id=id;
        this.username=username;
        this.password=password;
        this.locked=locked;
        this.createTime=createTime;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
