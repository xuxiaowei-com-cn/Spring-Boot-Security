package cn.com.xuxiaowei.springbootsecurity.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 短信验证码登录 认证管理程序
 *
 * @author xuxiaowei
 */
public class SmsWebsiteAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 用户名
        Object principal = authentication.getPrincipal();

        // 密码
        Object credentials = authentication.getCredentials();

        // 权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return new UsernamePasswordAuthenticationToken(principal, credentials, authorities);
    }

}
