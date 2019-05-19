package cn.com.xuxiaowei.springbootsecurity.controller;

import cn.com.xuxiaowei.springbootsecurity.config.LoginUrlAuthenticationEntryPointConfig;
import cn.com.xuxiaowei.springbootsecurity.entity.User;
import cn.com.xuxiaowei.springbootsecurity.service.IUserService;
import cn.com.xuxiaowei.springbootsecurity.util.md5.Md5Utils;
import cn.com.xuxiaowei.springbootsecurity.util.rsa.RsaUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Security RestController
 *
 * @author xuxiaowei
 */
@RestController
public class SecurityRestController {

    private final IUserService userService;

    @Autowired
    public SecurityRestController(IUserService userService) {
        this.userService = userService;
    }

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
     * 用户注册
     *
     * @param username       手机号
     * @param password       密码
     * @param repassPassword 确认密码
     * @param patchca        图片验证码
     * @param smsCode        短信验证码
     */
    @RequestMapping(value = "/reg.do", method = RequestMethod.POST)
    public Map<String, Object> reg(HttpServletRequest request, HttpServletResponse response,
                                   String username, String password, String repassPassword,
                                   String patchca, String smsCode) {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        // 默认返回状态为错误
        map.put("code", 1);

        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不为空！");
            return map;
        }

        if (StringUtils.isEmpty(username)) {
            map.put("msg", "手机号不为空！");
            return map;
        }

        if (StringUtils.isEmpty(smsCode)) {
            map.put("msg", "短信验证码不为空！");
            return map;
        }

        HttpSession session = request.getSession();

        String smsPhoneSession = (String) session.getAttribute("smsPhone");

        if (StringUtils.isEmpty(smsPhoneSession)) {
            map.put("msg", "用户注册受到攻击！");
            data.put("phone", "Session 中不存在已发送短信验证码的手机号：" + username);
            return map;
        }

        if (!username.equals(smsPhoneSession)) {
            map.put("msg", "手机号验证失败！");
            return map;
        }

        LocalDateTime smsCodeTimeSession = (LocalDateTime) session.getAttribute("smsCodeTime");

        if (smsCodeTimeSession == null) {
            map.put("msg", "用户注册受到攻击！");
            data.put("smsCodeTime", "Session 中不存在短信验证码的创建时间");
            return map;
        }

        LocalDateTime now = LocalDateTime.now();

        // 短信验证码的有效时间为 5 分钟
        LocalDateTime plus = smsCodeTimeSession.plus(5, ChronoUnit.MINUTES);

        if (plus.isBefore(now)) {
            map.put("msg", "短信验证码过期，请重新获取！");
            return map;
        }

        String smsCodeSession = (String) session.getAttribute("smsCode");

        if (StringUtils.isEmpty(smsCodeSession)) {
            map.put("msg", "用户注册受到攻击！");
            data.put("smsCode", "Session 中不存在发送给" + username + "的短信验证码");
            return map;
        }

        if (!smsCode.equals(smsCodeSession)) {
            map.put("msg", "短信验证码错误！");
            return map;
        }

        // 验证完信息之后，检查用户是否已存在
        User user = userService.getUsername(username);

        if (user != null) {
            map.put("msg", "注册失败，用户已存在，请登录！");
            return map;
        }

        try {
            String rsaPrivateKey = (String) session.getAttribute("RsaPrivateKey");
            password = new String(RsaUtils.decryptByPrivateKey(password, rsaPrivateKey));
        } catch (GeneralSecurityException e) {

            // <code>e.printStackTrace();</code>

            map.put("msg", "非法操作，刷新后再试！");
            data.put("RSA", "非对称性解密失败！");
            return map;

        }

        // 密码使用 MD5 加密后，储存在数据库中
        password = Md5Utils.getMD5(password);

        user = new User().setUsername(username).setPassword(password);

        boolean save = userService.save(user);

        if (save) {
            map.put("code", 0);
            map.put("msg", "注册成功");
        } else {
            map.put("msg", "注册失败，请稍后再试！");
            data.put("save", "储存数据时发生意外");
        }

        return map;
    }

}
