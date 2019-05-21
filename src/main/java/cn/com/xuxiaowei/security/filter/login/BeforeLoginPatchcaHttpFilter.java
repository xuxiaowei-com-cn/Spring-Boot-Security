package cn.com.xuxiaowei.security.filter.login;

import cn.com.xuxiaowei.security.setting.SecuritySettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 授权登录（用户名密码登录）前 验证图片验证码
 *
 * @author xuxiaowei
 */
@Slf4j
public class BeforeLoginPatchcaHttpFilter extends HttpFilter {

    @Autowired
    private SecuritySettings securitySettings;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 请求 URI
        String requestURI = request.getRequestURI();

        // 与登录请求URL相同
        String login = securitySettings.loginProcessingUrl;

        if (login.equals(requestURI)) {

            HttpSession session = request.getSession();

            // Session 中的图片验证码（要避免图片验证码为null时的错误）
            String patchcaSession = (session.getAttribute("patchca") + "").toUpperCase();

            if (StringUtils.isEmpty(patchcaSession)) {
                log.debug("用户登录受到攻击，Session 中无图片验证码！");
                response.sendRedirect(securitySettings.failurePatchcaUrl);
            }

            // 用户发送的图片验证码
            String patchca = request.getParameter("patchca").toUpperCase();

            if (StringUtils.isEmpty(patchca)) {
                log.debug("用户登录受到攻击，用户未发送图片验证码！");
                response.sendRedirect(securitySettings.failurePatchcaUrl);
            }

            // 图片验证码验证成功
            if (patchcaSession.equals(patchca)) {
                chain.doFilter(request, response);
            } else {
                log.debug("用户登录失败，图片验证码验证失败！");
                response.sendRedirect(securitySettings.failurePatchcaUrl);
            }

        } else {
            chain.doFilter(request, response);
        }

    }

}
