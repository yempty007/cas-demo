package com.example.cas.server.repository;

import com.example.cas.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description UserRepository
 * @Author yempty
 * @Date 2020/8/31 15:17
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return
     */
    @Query("select new User(id,username,locked,createTime) from User where username=:username")
    User findByUsername(@Param("username") String username);

    /**
     * 更改锁定状态
     *
     * @param id     ID
     * @param status 状态值
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("update User set locked=:status where id=:id")
    void changeLockedStatus(@Param("id") Long id, @Param("status") Integer status);
}
