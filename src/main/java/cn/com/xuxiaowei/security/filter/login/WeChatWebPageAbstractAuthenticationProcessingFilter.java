package cn.com.xuxiaowei.security.filter.login;

import cn.com.xuxiaowei.security.entity.WeChat;
import cn.com.xuxiaowei.security.service.IWeChatService;
import cn.com.xuxiaowei.security.setting.SecuritySettings;
import cn.com.xuxiaowei.security.util.cookie.CookieUtils;
import cn.com.xuxiaowei.security.util.response.ResponseUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.weixin4j.Weixin;
import org.weixin4j.WeixinException;
import org.weixin4j.component.SnsComponent;
import org.weixin4j.model.sns.SnsUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第三方登录（微信网页（微信内部））认证处理过滤器
 *
 * @author xuxiaowei
 */
@Slf4j
public class WeChatWebPageAbstractAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private SecuritySettings securitySettings;

    @Autowired
    private IWeChatService weChatService;

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

        // 微信平台基础支持对象
        Weixin weixin = new Weixin();

        // 网页授权基础支持
        SnsComponent snsComponent = new SnsComponent(weixin);

        try {

            // 拉取用户信息
            SnsUser snsUser = snsComponent.getSnsUserByCode(code);

            // 由于 org.weixin4j.model.sns.SnsUser 没有重写 toString ,所以将其转化为 JSON
            Object snsUserJson = JSON.toJSON(snsUser);

            log.debug("");
            log.debug(snsUserJson.toString());
            log.debug("");

            // OpenID
            String openid = snsUser.getOpenid();

            // 根据 openId，查询 WeChat
            WeChat weChat = weChatService.getOpenId(openid);

            if (weChat == null) {

                // 创建 WeChat 实体类，用于 强制类型转换
                // 强制类型转换为空时，会出现异常
                weChat = new WeChat();

                // 强制类型转换 org.springframework.beans.BeanUtils
                BeanUtils.copyProperties(snsUser, weChat);

                // 保存 微信网页（微信内部） 数据
                boolean save = weChatService.save(weChat);

                // 保存成功
                if (save) {
                    log.debug("微信网页（微信内部） 数据保存成功：" + weChat);
                } else {
                    // 保存失败
                    log.debug("微信网页（微信内部） 数据保存失败：" + weChat);
                }

            }

            List<GrantedAuthority> authorities = new ArrayList<>();

            // 给一个权限
            authorities.add(new SimpleGrantedAuthority("ROLE_WECHATWEBPAGE"));


            // 注意此时的类型 org.springframework.security.core.userdetails.User
            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(openid, "", authorities);

            // 记录远程地址，如果会话已存在（也不会创建会话），还会设置会话ID。
            // 由于 第三方登录（微信网页（微信内部））授权时，是从第三方页面跳转的进入本站的，故授权时，没有 getDetails()
            // 第三方登录（微信网页（微信内部））授权时，必须进入 WeChatWebPageHttpServlet
            // detailsWeChatWebPage 是在 WeChatWebPageHttpServlet 中创建的，并放入 Session 中
            Object detailsWeChatWebPage = session.getAttribute("detailsWeChatWebPage");
            WebAuthenticationDetails details = null;
            if (detailsWeChatWebPage instanceof WebAuthenticationDetails) {
                details = (WebAuthenticationDetails) detailsWeChatWebPage;
            }

            // principal    用户 org.springframework.security.core.userdetails.User
            // credentials  自定义信息、如：WebAuthenticationDetails
            // authorities  权限
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, details, authorities);

            // 保持登录 3 天
            CookieUtils.setSessionCookieTime(request, response, securitySettings.weChatPageTokenValiditySeconds);

            // 返回验证结果
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (WeixinException e) {
            e.printStackTrace();
        }

        return null;
    }

}
