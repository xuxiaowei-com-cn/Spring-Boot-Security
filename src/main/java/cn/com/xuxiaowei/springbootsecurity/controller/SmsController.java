package cn.com.xuxiaowei.springbootsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信验证码 Controller
 *
 * @author xuxiaowei
 */
@Slf4j
@Controller
public class SmsController {

    /**
     * 短信登录页面
     */
    @RequestMapping("/sms")
    public String sms(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sms";
    }

}
