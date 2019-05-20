package cn.com.xuxiaowei.springbootsecurity.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 第三方登录（微信扫码）认证管理程序
 *
 * @author xuxiaowei
 */
public class WeChatWebsiteAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 用户 org.springframework.security.core.userdetails.User
        Object principal = authentication.getPrincipal();

        // 自定义信息、如：WebAuthenticationDetails
        Object credentials = authentication.getCredentials();

        // 权限/角色
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return new UsernamePasswordAuthenticationToken(principal, credentials, authorities);
    }

}
