package cn.com.xuxiaowei.springbootsecurity.filter.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 第三方登录（微信网页（微信内部））认证处理过滤器
 *
 * @author xuxiaowei
 */
public class WeChatWebPageAbstractAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public WeChatWebPageAbstractAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {


        return null;
    }

}
