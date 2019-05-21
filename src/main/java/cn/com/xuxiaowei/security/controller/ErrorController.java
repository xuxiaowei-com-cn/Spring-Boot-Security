package cn.com.xuxiaowei.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Error Controller
 *
 * @author xuxiaowei
 */
@Controller
public class ErrorController {

    /**
     * 404 页面
     */
    @RequestMapping("/404")
    public String notFound(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "error/" + HttpStatus.NOT_FOUND.value();
    }

    /**
     * 302 页面
     */
    @RequestMapping("/302")
    public String found(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "error/" + HttpStatus.FOUND.value();
    }

}
