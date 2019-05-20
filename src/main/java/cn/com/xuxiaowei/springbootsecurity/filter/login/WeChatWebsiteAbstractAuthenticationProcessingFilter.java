package cn.com.xuxiaowei.springbootsecurity.filter.login;

import cn.com.xuxiaowei.springbootsecurity.setting.WeChatOpenSettings;
import cn.com.xuxiaowei.springbootsecurity.util.response.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 第三方登录（微信扫码）认证处理过滤器
 *
 * @author xuxiaowei
 */
@Slf4j
public class WeChatWebsiteAbstractAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private WeChatOpenSettings weChatOpenSettings;

    public WeChatWebsiteAbstractAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        HttpSession session = request.getSession();

        String wechatWebsiteConnectState = (String) session.getAttribute("wechat_website_connect_state");

        String code = request.getParameter("code");

        String state = request.getParameter("state");

        if (StringUtils.isEmpty(wechatWebsiteConnectState)) {
            map.put("msg", "微信扫码 登录受到攻击！");
            data.put("details", "Session 中不存在 微信扫码 登录的状态码！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        if (StringUtils.isEmpty(code)) {
            map.put("msg", "微信扫码 登录受到攻击！");
            data.put("details", "未收到 微信扫码 登录的 code！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        if (StringUtils.isEmpty(state)) {
            map.put("msg", "微信扫码 登录受到攻击！");
            data.put("details", "未收到 微信扫码 登录的状态码！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        if (!state.equals(wechatWebsiteConnectState)) {
            map.put("msg", "微信扫码 登录受到攻击！");
            data.put("details", "Session 中的 微信扫码 登录的状态码与接收的不同！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }


        return null;
    }

}
