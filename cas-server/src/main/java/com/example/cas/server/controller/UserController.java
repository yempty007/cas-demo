package com.example.cas.server.controller;

import com.example.cas.server.common.result.Result;
import com.example.cas.server.dto.UserDTO;
import com.example.cas.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @Description UserController
 * @Author yempty
 * @Date 2020/8/31 14:59
 */
@RequestMapping("users")
@RestController
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @GetMapping
    public Result<UserDTO> findUser(String username) {
        UserDTO userDTO = userService.findByUsername(username);
        return Result.ok(userDTO);
    }

    @PostMapping
    public Result<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return Result.okMsg("新增用户成功");
    }

    @PutMapping("password")
    public Result<UserDTO> resetPassword(@RequestBody UserDTO userDTO) {
        userService.resetPassword(userDTO);
        return Result.okMsg("密码重置成功");
    }

    @PutMapping("locked/{id}")
    public Result<UserDTO> locked(@PathVariable("id") Long id) {
        userService.lockedUser(id);
        return Result.okMsg("用户锁定成功");
    }

    @PutMapping("unlocked/{id}")
    public Result<UserDTO> unlocked(@PathVariable("id") Long id) {
        userService.unLockedUser(id);
        return Result.okMsg("用户解锁成功");
    }

}
