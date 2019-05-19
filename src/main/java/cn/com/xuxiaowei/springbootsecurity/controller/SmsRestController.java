package cn.com.xuxiaowei.springbootsecurity.controller;

import cn.com.xuxiaowei.springbootsecurity.entity.User;
import cn.com.xuxiaowei.springbootsecurity.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信验证码 RestController
 *
 * @author xuxiaowei
 */
@Slf4j
@RestController
@RequestMapping("/sms")
public class SmsRestController {

    private final IUserService userService;

    @Autowired
    public SmsRestController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 短信登录
     *
     * @param username 手机号
     * @param patchca  图片验证码
     * @param smsCode  短信验证码
     */
    @RequestMapping("/login.do")
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response,
                                     String username, String patchca, String smsCode) {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        map.put("code", 1);

        if (StringUtils.isEmpty(smsCode)) {
            map.put("msg", "短信验证码不为空！");
            return map;
        }

        if (StringUtils.isEmpty(username)) {
            map.put("msg", "手机号不为空！");
            return map;
        }

        HttpSession session = request.getSession();

        String smsPhoneSession = (String) session.getAttribute("smsPhone");

        if (StringUtils.isEmpty(smsPhoneSession)) {
            map.put("msg", "用户短信登录受到攻击！");
            data.put("phone", "Session 中不存在已发送短信验证码的手机号：" + username);
            return map;
        }

        if (!username.equals(smsPhoneSession)) {
            map.put("msg", "手机号验证失败！");
            return map;
        }

        LocalDateTime smsCodeTimeSession = (LocalDateTime) session.getAttribute("smsCodeTime");

        if (smsCodeTimeSession == null) {
            map.put("msg", "用户短信登录受到攻击！");
            data.put("smsCodeTime", "Session 中不存在短信验证码的创建时间");
            return map;
        }

        // 短信验证码的有效时间为 5 分钟
        LocalDateTime plus = smsCodeTimeSession.plus(5, ChronoUnit.MINUTES);

        LocalDateTime now = LocalDateTime.now();

        if (plus.isBefore(now)) {
            map.put("msg", "短信验证码过期，请重新获取！");
            return map;
        }

        String smsCodeSession = (String) session.getAttribute("smsCode");

        if (StringUtils.isEmpty(smsCodeSession)) {
            map.put("msg", "用户短信登录受到攻击！");
            data.put("smsCode", "Session 中不存在发送给" + username + "的短信验证码");
            return map;
        }

        if (!smsCode.equals(smsCodeSession)) {
            map.put("msg", "短信验证码错误！");
            return map;
        }

        User user = userService.getUsername(username);

        if (user == null) {

            user = new User().setUsername(username);

            boolean save = userService.save(user);

            if (save) {
                map.put("msg", "用户创建成功，短信登录成功");
            } else {
                map.put("msg", "用户创建失败，短信登录失败！");
                return map;
            }
        } else {
            map.put("msg", "短信登录成功");
        }

        map.put("code", 0);

        return map;
    }

    /**
     * 发送短信验证码
     * <p>
     * 发送短信验证码之前，验证图片验证码，防止短信验证码被攻击
     *
     * @param phone   发送短信验证码的手机号
     * @param patchca 发送短信验证码之前需要验证的图片验证码
     */
    @RequestMapping(value = "/send.do", method = RequestMethod.POST)
    public Map<String, Object> send(HttpServletRequest request, HttpServletResponse response,
                                    String phone, String patchca) {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        // 默认返回状态为错误
        map.put("code", 1);

        if (StringUtils.isEmpty(phone)) {
            map.put("msg", "手机号不为空！");
            return map;
        }

        if (StringUtils.isEmpty(patchca)) {
            map.put("msg", "图片验证码不为空！");
            return map;
        } else {
            patchca = patchca.toUpperCase();
        }

        HttpSession session = request.getSession();

        String patchcaSession = ((String) session.getAttribute("patchca")).toUpperCase();

        if (StringUtils.isEmpty(patchcaSession)) {
            map.put("msg", "发送短信验证码受到攻击！");
            data.put("patchca", "Session 中不存在图片验证码！");
            return map;
        }

        if (patchca.equals(patchcaSession)) {

            // 手机号放入 Session
            session.setAttribute("smsPhone", phone);

            // 手机号放入 Session 的时间（name 中包含手机号）
            session.setAttribute("smsCodeTime", LocalDateTime.now());

            // 6 位随机数，在 100000 和 999999 之间
            String in100000to999999 = (int) ((Math.random() * 9 + 1) * 100000) + "";

            // 将验证码放入 Session
            session.setAttribute("smsCode", in100000to999999);

            log.debug("");
            log.debug("发送短信验证码成功");
            log.debug("手机号：" + phone);
            log.debug("验证码：" + in100000to999999);
            log.debug("");

            map.put("code", 0);
            map.put("msg", "发送短信验证码成功");
        } else {
            map.put("msg", "发送短信验证码失败，图片验证码错误！");
        }

        return map;
    }

}
