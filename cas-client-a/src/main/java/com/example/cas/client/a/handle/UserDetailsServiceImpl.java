package com.example.cas.client.a.handle;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Description UserDetailsServiceImpl
 * @Author yempty
 * @Date 2020/8/26 11:09
 */
@Component
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new User(s, "123", true, true, true, true,
                AuthorityUtils.createAuthorityList("ROLE_user"));
    }
}
