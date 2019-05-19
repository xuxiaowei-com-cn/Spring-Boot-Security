package cn.com.xuxiaowei.springbootsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * 主页 Controller
 *
 * @author xuxiaowei
 */
@Controller
public class IndexController {

    /**
     * 主页页面
     */
    @RequestMapping("")
    public String reg(HttpServletRequest request, HttpServletResponse response, Model model) {

        // 安全信息
        SecurityContext context = SecurityContextHolder.getContext();

        // 认证信息
        Authentication authentication = context.getAuthentication();

        Object details = authentication.getDetails();

        String remoteAddress = null;

        // 短信登录为 null
        if (details != null) {
            WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) details;

            // 如果使用了 Nginx 代理，需要在 application.yml 配置
            // Nginx 配置详情请查看 cn.com.xuxiaowei.springbootsecurity.controller.README.md
            remoteAddress = webAuthenticationDetails.getRemoteAddress();
        }

        Object principal = authentication.getPrincipal();

        // 未登录时类型是字符串：anonymousUser
        // 登录时类型是 org.springframework.security.core.userdetails.User
        if (principal instanceof org.springframework.security.core.userdetails.User) {

            // 注意此时的 User 类型
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;

            String username = user.getUsername();

            Collection<GrantedAuthority> authorities = user.getAuthorities();

            Object[] authoritiesArray = authorities.toArray();

            model.addAttribute("username", username);
            model.addAttribute("remoteAddress", remoteAddress);
            model.addAttribute("authoritiesArray", authoritiesArray);
        }

        return "index";
    }

}
