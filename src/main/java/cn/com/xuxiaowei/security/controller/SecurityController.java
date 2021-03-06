package cn.com.xuxiaowei.security.controller;

import cn.com.xuxiaowei.security.config.LoginUrlAuthenticationEntryPointConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Security Controller
 *
 * @author xuxiaowei
 */
@Controller
public class SecurityController {

    /**
     * 登录页面
     * <p>
     * 自定义登录URL验证入口点 {@link LoginUrlAuthenticationEntryPointConfig}
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {

        // 获取 LoginUrlAuthenticationEntryPointConfig 中设置的 需要授权登录页面的 URL
        String redirectUrl = request.getParameter("redirectUrl");

        // 放入 Session，以备登录成功后使用
        request.getSession().setAttribute("redirectUrl", redirectUrl);

        return "login";
    }

}
