package cn.com.xuxiaowei.springbootsecurity.controller;

import cn.com.xuxiaowei.springbootsecurity.util.security.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        String name = SecurityUtils.getName();

        // 如果使用了 Nginx 代理，需要在 application.yml 配置
        // Nginx 配置详情请查看 cn.com.xuxiaowei.springbootsecurity.controller.README.md
        String remoteAddress = SecurityUtils.getRemoteAddress();

        GrantedAuthority[] grantedAuthoritys = SecurityUtils.getGrantedAuthoritys();

        model.addAttribute("username", name);
        model.addAttribute("remoteAddress", remoteAddress);
        model.addAttribute("authoritiesArray", grantedAuthoritys);

        return "index";
    }

}
