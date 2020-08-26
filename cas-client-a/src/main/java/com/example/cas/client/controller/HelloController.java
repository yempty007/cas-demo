package com.example.cas.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description HelloController
 * @Author yempty
 * @Date 2020/8/26 11:11
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
    @GetMapping("/user/hello")
    public String user() {
        return "user";
    }
}
