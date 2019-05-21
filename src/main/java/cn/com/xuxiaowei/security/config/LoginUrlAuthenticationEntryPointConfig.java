package cn.com.xuxiaowei.security.config;

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

        // 需要授权登录页面的 URL
        // 也可使用 <code>request.getRequestURI();</code>，这样 URL 会变得简短
        // 在 多个域名 的项目中，需要使用 <code>request.getRequestURL();</code>，因为 <code>request.getRequestURI();</code> 不含域名
        StringBuffer requestURL = request.getRequestURL();

        // 需要授权登录页面的 参数
        String queryString = request.getQueryString();

        if (queryString != null) {
            requestURL.append("?").append(queryString);
        }

        // 原始 需要重定向的 URL
        String buildRedirectUrlToLoginPage = super.buildRedirectUrlToLoginPage(request, response, authException);

        // 组合新的 URL
        // 可以使用随机数作为 name，重定向地址作为 value，放入 Session 中，再将 随机数放入到 参数中
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
