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
