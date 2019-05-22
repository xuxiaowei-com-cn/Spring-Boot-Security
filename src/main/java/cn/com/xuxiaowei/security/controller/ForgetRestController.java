package cn.com.xuxiaowei.security.controller;

import cn.com.xuxiaowei.security.entity.User;
import cn.com.xuxiaowei.security.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 找回密码 RestController
 *
 * @author xuxiaowei
 */
@RestController
public class ForgetRestController {

    private final IUserService userService;

    @Autowired
    public ForgetRestController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 找回密码
     *
     * @param username 手机号
     * @param patchca  图片验证码
     * @param smsCode  短信验证码
     */
    @RequestMapping("/forget.do")
    public Map<String, Object> forget(HttpServletRequest request, HttpServletResponse response,
                                      String username, String patchca, String smsCode) {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        // 默认返回状态为错误
        map.put("code", 1);

        HttpSession session = request.getSession();

        String smsPhoneSession = (String) session.getAttribute("smsPhone");

        LocalDateTime smsCodeTimeSession = (LocalDateTime) session.getAttribute("smsCodeTime");

        String smsCodeSession = (String) session.getAttribute("smsCode");

        //////////////////// 检查用户发送的数据 ////////////////////

        if (StringUtils.isEmpty(username)) {
            map.put("msg", "手机号不为空！");
            return map;
        }

        if (StringUtils.isEmpty(smsCode)) {
            map.put("msg", "短信验证码不为空！");
            return map;
        }

        //////////////////// 检查Session中的数据 ////////////////////

        if (StringUtils.isEmpty(smsPhoneSession)) {
            map.put("msg", "用户找回密码受到攻击！");
            data.put("phone", "Session 中不存在已发送短信验证码的手机号：" + username);
            return map;
        }

        if (StringUtils.isEmpty(smsCodeSession)) {
            map.put("msg", "用户注册受到攻击！");
            data.put("smsCode", "Session 中不存在发送给" + username + "的短信验证码");
            return map;
        }

        if (smsCodeTimeSession == null) {
            map.put("msg", "用户注册受到攻击！");
            data.put("smsCodeTime", "Session 中不存在短信验证码的创建时间");
            return map;
        }

        //////////////////// 验证数据 ////////////////////

        if (!username.equals(smsPhoneSession)) {
            map.put("msg", "手机号验证失败！");
            return map;
        }

        if (!smsCode.equals(smsCodeSession)) {
            map.put("msg", "短信验证码错误！");
            return map;
        }

        LocalDateTime now = LocalDateTime.now();

        // 短信验证码的有效时间为 5 分钟
        LocalDateTime plus = smsCodeTimeSession.plus(5, ChronoUnit.MINUTES);

        if (plus.isBefore(now)) {
            map.put("msg", "短信验证码过期，请重新获取！");
            return map;
        }

        //////////////////// 验证完手机号的相关信息（短信验证码等），查询用户，是否已注册 ////////////////////

        User user = userService.getPhone(username);

        if (user == null) {
            map.put("msg", "用户未注册，请注册！");
            return map;
        } else {

            // 成功返回状态
            map.put("code", 0);

            // 随机数，重置密码凭证
            String resetPassProof = UUID.randomUUID().toString().replace("-", "");

            // 重置密码凭证
            // 控制是否能进入重置密码页面
            // 只能用一次
            session.setAttribute("resetPassProof", resetPassProof);

            data.put("resetPassProof", resetPassProof);

            map.put("msg", "短信验证成功");
            return map;
        }

    }

}
