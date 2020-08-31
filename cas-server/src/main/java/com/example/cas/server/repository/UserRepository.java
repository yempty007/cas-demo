package com.example.cas.server.repository;

import com.example.cas.server.dto.UserDTO;
import com.example.cas.server.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description UserRepository
 * @Author yempty
 * @Date 2020/8/31 15:17
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return
     */
    @Query("select id,username,locked,create_time from cas_user where username=:username")
    UserDTO findByUsername(String username);

    /**
     * 更改锁定状态
     *
     * @param id     ID
     * @param status 状态值
     */
    @Modifying
    @Query("update cas_user set locked=:status where id=:id")
    void changeLockedStatus(Long id, Integer status);
}
