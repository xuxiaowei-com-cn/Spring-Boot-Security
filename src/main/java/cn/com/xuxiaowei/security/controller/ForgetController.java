package cn.com.xuxiaowei.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 忘记密码 Controller
 *
 * @author xuxiaowei
 */
@Controller
public class ForgetController {

    /**
     * 忘记密码页面
     */
    @RequestMapping("/forget")
    public String forget(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "forget";
    }

}
