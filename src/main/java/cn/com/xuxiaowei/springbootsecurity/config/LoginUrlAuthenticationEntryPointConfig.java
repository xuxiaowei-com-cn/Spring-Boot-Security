package cn.com.xuxiaowei.springbootsecurity.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录URL验证入口点
 * <p>
 * 当且仅当访问需要授权的页面时，才使用此类
 *
 * @author xuxiaowei
 */
public class LoginUrlAuthenticationEntryPointConfig extends LoginUrlAuthenticationEntryPoint {

    /**
     * 必须重写
     *
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public LoginUrlAuthenticationEntryPointConfig(String loginFormUrl) {
        super(loginFormUrl);
    }

    /**
     * 当且仅当访问需要授权的页面时，才使用此方法
     */
    @Override
    protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {

        // 获取你在 WebSecurityConfigurerAdapter 继承类中配置的 登录页面 URL
        String loginForm = determineUrlToUseForThisRequest(request, response, authException);

        // 需要授权登录页面的 URL
        StringBuffer requestURL = request.getRequestURL();

        // 需要授权登录页面的 参数
        String queryString = request.getQueryString();

        if (queryString != null) {
            requestURL.append("?").append(queryString);
        }

        String buildRedirectUrlToLoginPage = super.buildRedirectUrlToLoginPage(request, response, authException);

        buildRedirectUrlToLoginPage += "?redirectUrl=" + requestURL;

        return buildRedirectUrlToLoginPage;

        // 原始
        // <code>return super.buildRedirectUrlToLoginPage(request, response, authException);</code>
    }

    @Override
    protected String buildHttpsRedirectUrlForRequest(HttpServletRequest request) throws IOException, ServletException {
        return super.buildHttpsRedirectUrlForRequest(request);
    }

}
