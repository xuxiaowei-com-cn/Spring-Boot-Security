package cn.com.xuxiaowei.springbootsecurity.filter.login;

import cn.com.xuxiaowei.springbootsecurity.entity.User;
import cn.com.xuxiaowei.springbootsecurity.service.IUserService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信验证码登录 认证处理过滤器
 *
 * @author xuxiaowei
 */
public class SmsAbstractAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private IUserService userService;

    public SmsAbstractAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        HttpSession session = request.getSession();

        String username = request.getParameter("username");

        String patchca = request.getParameter("patchca");

        String smsCode = request.getParameter("smsCode");

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        map.put("code", 1);

        if (StringUtils.isEmpty(smsCode)) {
            map.put("msg", "短信验证码不为空！");
            response(response, map);
            return null;
        }

        if (StringUtils.isEmpty(username)) {
            map.put("msg", "手机号不为空！");
            response(response, map);
            return null;
        }

        String smsPhoneSession = (String) session.getAttribute("smsPhone");

        if (StringUtils.isEmpty(smsPhoneSession)) {
            map.put("msg", "用户短信登录受到攻击！");
            data.put("phone", "Session 中不存在已发送短信验证码的手机号：" + username);
            response(response, map);
            return null;
        }

        if (!username.equals(smsPhoneSession)) {
            map.put("msg", "手机号验证失败！");
            response(response, map);
            return null;
        }

        LocalDateTime smsCodeTimeSession = (LocalDateTime) session.getAttribute("smsCodeTime");

        if (smsCodeTimeSession == null) {
            map.put("msg", "用户短信登录受到攻击！");
            data.put("smsCodeTime", "Session 中不存在短信验证码的创建时间");
            response(response, map);
            return null;
        }

        // 短信验证码的有效时间为 5 分钟
        LocalDateTime plus = smsCodeTimeSession.plus(5, ChronoUnit.MINUTES);

        LocalDateTime now = LocalDateTime.now();

        if (plus.isBefore(now)) {
            map.put("msg", "短信验证码过期，请重新获取！");
            response(response, map);
            return null;
        }

        String smsCodeSession = (String) session.getAttribute("smsCode");

        if (StringUtils.isEmpty(smsCodeSession)) {
            map.put("msg", "用户短信登录受到攻击！");
            data.put("smsCode", "Session 中不存在发送给" + username + "的短信验证码");
            response(response, map);
            return null;
        }

        if (!smsCode.equals(smsCodeSession)) {
            map.put("msg", "短信验证码错误！");
            response(response, map);
            return null;
        }

        User userInfo = userService.getUsername(username);

        if (userInfo == null) {

            userInfo = new User().setUsername(username);

            boolean save = userService.save(userInfo);

            if (save) {
                map.put("msg", "用户创建成功，短信登录成功");
            } else {
                map.put("msg", "用户创建失败，短信登录失败！");
                response(response, map);
                return null;
            }
        } else {
            map.put("msg", "短信登录成功");
        }

        map.put("code", 0);

//        response(response, map);

        List<GrantedAuthority> authorities = new ArrayList<>();

        // 给一个权限
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        String password = userInfo.getPassword();

        // 注意此时的类型 org.springframework.security.core.userdetails.User
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(username, password, authorities);

        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();

        WebAuthenticationDetails webAuthenticationDetails = null;

        if (details instanceof WebAuthenticationDetails) {
            webAuthenticationDetails = (WebAuthenticationDetails) details;
        }

        // principal    用户名
        // credentials  密码
        // authorities  权限
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, webAuthenticationDetails, authorities);

        // 短信验证码登录成功后，自动重定向到主页页面
        // 由于 短信验证码登录 时，前端需要接收登录结果的数据，所以将 短信验证码登录 状态放入 Session 中
        // 在 进入主页时，先进入拦截器，判断刚刚是否是 短信验证码登录，如果是，返回 前端需要接收的登录结果数据
        // 此 Session 值只用一次，用完置 null
        session.setAttribute("loginFilter", "smsFilter");

        // 返回验证结果
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 响应 JSON 数据
     */
    private void response(HttpServletResponse response, Map map) throws IOException {

        Object o = JSON.toJSON(map);

        response.setContentType("text/json;charset=UTF-8");

        response.getWriter().println(o);
        response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();

    }

}
