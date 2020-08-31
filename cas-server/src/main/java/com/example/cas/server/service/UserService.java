package com.example.cas.server.service;

import com.example.cas.server.dto.UserDTO;

import java.util.List;

/**
 * @Description UserService
 * @Author yempty
 * @Date 2020/8/31 15:25
 */
public interface UserService {

    /**
     * 根据ID查找用户
     *
     * @param id ID
     * @return
     */
    UserDTO findById(Long id);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return
     */
    UserDTO findByUsername(String username);

    /**
     * 分页查找用户
     *
     * @param page 页码
     * @param size 每页记录数
     * @return
     */
    List<UserDTO> findAllUserByPage(int page, int size);

    /**
     * 新增用户
     *
     * @param dto 用户数据DTO
     */
    void saveUser(UserDTO dto);

    /**
     * 重置密码
     *
     * @param dto 用户数据DTO
     */
    void resetPassword(UserDTO dto);

    /**
     * 锁定用户
     *
     * @param id ID
     */
    void lockedUser(Long id);

    /**
     * 解锁用户
     *
     * @param id ID
     */
    void unLockedUser(Long id);

}
