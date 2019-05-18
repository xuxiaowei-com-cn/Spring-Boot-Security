package cn.com.xuxiaowei.springbootsecurity.controller;

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
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "login";
    }

    /**
     * 注册页面
     */
    @RequestMapping("/reg")
    public String reg(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "reg";
    }

}
