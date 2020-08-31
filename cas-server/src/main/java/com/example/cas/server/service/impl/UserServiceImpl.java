package com.example.cas.server.service.impl;

import com.example.cas.server.common.convert.ConvertUtil;
import com.example.cas.server.common.enums.LockedStatusEnum;
import com.example.cas.server.common.exception.GlobalException;
import com.example.cas.server.dto.UserDTO;
import com.example.cas.server.entity.User;
import com.example.cas.server.repository.UserRepository;
import com.example.cas.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description UserServiceImpl
 * @Author yempty
 * @Date 2020/8/31 15:26
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findOne(id);
        UserDTO userDTO = ConvertUtil.sourceToTarget(user, UserDTO.class);
        return userDTO;
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserDTO userDTO = ConvertUtil.sourceToTarget(user, UserDTO.class);
        return userDTO;
    }

    @Override
    public List<UserDTO> findAllUserByPage(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<User> users = userRepository.findAll(pageable);
        return null;
    }

    @Override
    public void saveUser(UserDTO dto) {
        UserDTO userDTO = this.findByUsername(dto.getUsername());
        if (userDTO != null) {
            throw new GlobalException("新增失败,此用户名已被注册使用.");
        }
        User user = ConvertUtil.sourceToTarget(dto, User.class);

        String encodePassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodePassword);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(UserDTO dto) {
        UserDTO userDTO = this.findByUsername(dto.getUsername());
        if (userDTO == null) {
            throw new GlobalException("重置密码失败,此用户不存在.");
        }
        User user = ConvertUtil.sourceToTarget(dto, User.class);
        String encodePassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodePassword);
        userRepository.save(user);
    }

    @Override
    public void lockedUser(Long id) {
        userRepository.changeLockedStatus(id, LockedStatusEnum.LOCKED.getValue());
    }

    @Override
    public void unLockedUser(Long id) {
        userRepository.changeLockedStatus(id, LockedStatusEnum.UN_LOCKED.getValue());
    }
}
