package com.example.cas.server.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description
 * @Author yempty
 * @Date 2020/9/1 00:38
 * @Version 1.0
 */
@ComponentScan(basePackages = {"com.example.cas.server"})
@Configurable
public class WebConfig {

    /**
     * 加密编码器
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
