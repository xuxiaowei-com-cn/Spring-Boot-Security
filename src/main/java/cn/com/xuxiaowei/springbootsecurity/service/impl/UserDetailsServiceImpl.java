package cn.com.xuxiaowei.springbootsecurity.service.impl;

import cn.com.xuxiaowei.springbootsecurity.entity.User;
import cn.com.xuxiaowei.springbootsecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

/**
 * 用户服务详情
 * <p>
 * 角色需要有前缀（ROLE_）
 *
 * @author xuxiaowei
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService userService;

    @Autowired
    public UserDetailsServiceImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 密码，用于比较（如果用户未空，返回随机密码（不可预测））
        String password = UUID.randomUUID().toString();

        // 创建一个权限集合
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();

        // 查询用户（仅有一条数据，多数据报错）
        User user = userService.getUsername(username);

        if (user != null) {

            // 获取数据库中的密码
            password = user.getPassword();

            // 用户默认权限
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new org.springframework.security.core.userdetails.User(username, password, authorities);
    }
}
