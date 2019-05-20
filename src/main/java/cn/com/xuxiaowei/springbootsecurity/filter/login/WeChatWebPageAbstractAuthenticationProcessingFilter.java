package cn.com.xuxiaowei.springbootsecurity.filter.login;

import cn.com.xuxiaowei.springbootsecurity.util.response.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 第三方登录（微信网页（微信内部））认证处理过滤器
 *
 * @author xuxiaowei
 */
@Slf4j
public class WeChatWebPageAbstractAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public WeChatWebPageAbstractAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        HttpSession session = request.getSession();

        String wechatWebpageConnectState = (String) session.getAttribute("wechat_webpage_connect_state");

        String code = request.getParameter("code");

        String state = request.getParameter("state");

        if (StringUtils.isEmpty(wechatWebpageConnectState)) {
            map.put("msg", "微信网页（微信内部）登录受到攻击！");
            data.put("details", "Session 中不存在 微信网页（微信内部） 登录的状态码！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        if (StringUtils.isEmpty(code)) {
            map.put("msg", "微信网页（微信内部）登录受到攻击！");
            data.put("details", "未收到 微信网页（微信内部） 登录的 code！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        if (StringUtils.isEmpty(state)) {
            map.put("msg", "微信网页（微信内部）登录受到攻击！");
            data.put("details", "未收到 微信网页（微信内部） 登录的状态码！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        if (!state.equals(wechatWebpageConnectState)) {
            map.put("msg", "微信网页（微信内部）登录受到攻击！");
            data.put("details", "Session 中的 微信网页（微信内部） 登录的状态码与接收的不同！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }


        return null;
    }

}
