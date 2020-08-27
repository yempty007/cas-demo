package com.example.cas.client.b.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @Description TestController
 * @Author yempty
 * @Date 2020/8/27 17:22
 */
@RestController
public class TestController {

    @RequestMapping("logout")
    public void logout() {
        System.out.println( LocalDateTime.now()+"come on!!!!" );
    }
}
