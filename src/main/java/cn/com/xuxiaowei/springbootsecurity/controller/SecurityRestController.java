package cn.com.xuxiaowei.springbootsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Security RestController
 *
 * @author xuxiaowei
 */
@RestController
public class SecurityRestController {

    /**
     * 登录失败
     */
    @RequestMapping("/fail.do")
    public Map<String, Object> fail(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        data.put("msg", "用户名或密码不正确！");

        return map;
    }

    /**
     * 注册
     */
    @RequestMapping(value = "/reg.do", method = RequestMethod.POST)
    public Map<String, Object> reg(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        data.put("msg", "");

        return map;
    }

}
