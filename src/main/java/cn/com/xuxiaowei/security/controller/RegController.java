package cn.com.xuxiaowei.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注册 Controller
 *
 * @author xuxiaowei
 */
@Slf4j
@Controller
public class RegController {

    /**
     * 注册页面
     */
    @RequestMapping("/reg")
    public String reg(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "reg";
    }

}
