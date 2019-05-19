package cn.com.xuxiaowei.springbootsecurity.controller;

import cn.com.xuxiaowei.springbootsecurity.config.LoginUrlAuthenticationEntryPointConfig;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * 登录成功
     * <p>
     * redirectUrl：设置该Session值的类{@link LoginUrlAuthenticationEntryPointConfig}
     */
    @RequestMapping("/success.do")
    public Map<String, Object> success(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        // 需要登录后重定向的地址
        String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");

        // 判断是否为链接
        if (UrlUtils.isAbsoluteUrl(redirectUrl)) {
            data.put("redirectUrl", redirectUrl);
        } else {
            data.put("redirectUrl", "/");
        }

        map.put("code", 0);
        map.put("msg", "登录成功");

        return map;
    }

    /**
     * 登录失败
     */
    @RequestMapping("/fail.do")
    public Map<String, Object> fail(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        map.put("code", 1);
        map.put("msg", "用户名或密码不正确！");

        return map;
    }

    /**
     * 图片验证码验证失败
     */
    @RequestMapping("/fail/patchca.do")
    public Map<String, Object> failPatchca(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        map.put("code", 1);
        map.put("msg", "图片验证码验证失败！");

        map.put("changePatchca", 1);

        return map;
    }

    /**
     * 退出登录成功
     */
    @RequestMapping("/logout/success.do")
    public Map<String, Object> logoutSuccess(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        map.put("code", 0);
        map.put("msg", "退出成功");

        return map;
    }

}
