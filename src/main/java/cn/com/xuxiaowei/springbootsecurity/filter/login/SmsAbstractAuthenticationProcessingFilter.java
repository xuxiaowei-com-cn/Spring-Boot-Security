package cn.com.xuxiaowei.springbootsecurity.filter.login;

import cn.com.xuxiaowei.springbootsecurity.entity.User;
import cn.com.xuxiaowei.springbootsecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 短信验证码登录 认证处理过滤器
 *
 * @author xuxiaowei
 */
public class SmsAbstractAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private IUserService userService;

    public SmsAbstractAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        List<GrantedAuthority> authorities = new ArrayList<>();

        // 给一个权限
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        String username = request.getParameter("username");

        User userInfo = userService.getUsername(username);

        String password = userInfo.getPassword();

        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(username, password, authorities);

        // principal    用户名
        // credentials  密码
        // authorities  权限
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, null, authorities);

        // 返回验证结果
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
