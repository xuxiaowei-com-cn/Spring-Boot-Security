package cn.com.xuxiaowei.springbootsecurity.handlerinterceptor;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信登录成功 拦截器
 * <p>
 * 短信登录，保持 Cookie Session 有效期为 2 天
 * <p>
 * 短信验证码登录成功后，自动重定向到主页页面
 * 由于 短信验证码登录 时，前端需要接收登录结果的数据，所以将 短信验证码登录 状态放入 Session 中
 * 在 进入主页时，先进入拦截器，判断刚刚是否是 短信验证码登录，如果是，返回 前端需要接收的登录结果数据
 * Session 值只用一次，用完置 null
 *
 * @author xuxiaowei
 */
public class SmsLoginSuccessHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean b;

        HttpSession session = request.getSession();

        String loginFilter = (String) session.getAttribute("loginFilter");

        String smsFilter = "smsFilter";

        if (smsFilter.equals(loginFilter)) {

            // 2 天
            int second = 60 * 60 * 24 * 2;

            // 保持登录 2 天
            setCookie(request, response, second);

            Map<String, Object> map = new HashMap<>(4);
            Map<String, Object> data = new HashMap<>(4);
            map.put("data", data);

            map.put("code", 0);
            map.put("msg", "短信登录成功");

            Object o = JSON.toJSON(map);

            response.setContentType("text/json;charset=UTF-8");

            response.getWriter().println(o);
            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();

            session.setAttribute("loginFilter", null);

            b = false;
        } else {
            b = true;
        }

        return b;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 设置 短信验证码登录成功 后的 Cookie Session 时间
     *
     * @param second 秒
     */
    private void setCookie(HttpServletRequest request, HttpServletResponse response, int second) {

        String jSessionId = "JSESSIONID";

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                String name = c.getName();
                if (jSessionId.equals(name)) {
                    c.setMaxAge(second);
                    response.addCookie(c);
                }
            }
        }
    }

}
