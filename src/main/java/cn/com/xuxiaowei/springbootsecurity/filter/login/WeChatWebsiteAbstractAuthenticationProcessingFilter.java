package cn.com.xuxiaowei.springbootsecurity.filter.login;

import cn.com.xuxiaowei.springbootsecurity.entity.WeChat;
import cn.com.xuxiaowei.springbootsecurity.service.IWeChatService;
import cn.com.xuxiaowei.springbootsecurity.setting.SecuritySettings;
import cn.com.xuxiaowei.springbootsecurity.setting.WeChatOpenSettings;
import cn.com.xuxiaowei.springbootsecurity.util.cookie.CookieUtils;
import cn.com.xuxiaowei.springbootsecurity.util.response.ResponseUtils;
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
 * 第三方登录（微信扫码）认证处理过滤器
 *
 * @author xuxiaowei
 */
@Slf4j
public class WeChatWebsiteAbstractAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private SecuritySettings securitySettings;

    @Autowired
    private WeChatOpenSettings weChatOpenSettings;

    @Autowired
    private IWeChatService weChatService;

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

        // AppID、appSecret
        String appId = weChatOpenSettings.appId;
        String appSecret = weChatOpenSettings.appSecret;
        String authorizeUrl = weChatOpenSettings.authorizeUrl;

        // 微信平台基础支持对象
        Weixin weixin = new Weixin(appId, appSecret);

        // 网页授权基础支持
        SnsComponent snsComponent = new SnsComponent(weixin, authorizeUrl);

        try {

            // 拉取用户信息
            SnsUser snsUser = snsComponent.getSnsUserByCode(code);

            // OpenID
            String openid = snsUser.getOpenid();

            // 由于 org.weixin4j.model.sns.SnsUser 没有重写 toString ,所以将其转化为 JSON
            Object snsUserJson = JSON.toJSON(snsUser);

            log.debug("");
            log.debug(snsUserJson.toString());
            log.debug("");

            // 根据 openId，查询 WeChat
            WeChat weChat = weChatService.getOpenId(openid);

            if (weChat == null) {

                // 创建 WeChat 实体类，用于 强制类型转换
                // 强制类型转换为空时，会出现异常
                weChat = new WeChat();

                // 强制类型转换 org.springframework.beans.BeanUtils
                BeanUtils.copyProperties(snsUser, weChat);

                // 保存 微信扫码 数据
                boolean save = weChatService.save(weChat);

                // 保存成功
                if (save) {
                    log.debug("微信扫码 数据保存成功：" + weChat);
                } else {
                    // 保存失败
                    log.debug("微信扫码 数据保存失败：" + weChat);
                }

            }

            List<GrantedAuthority> authorities = new ArrayList<>();

            // 给一个权限
            authorities.add(new SimpleGrantedAuthority("ROLE_WECHATWEBSITE"));


            // 注意此时的类型 org.springframework.security.core.userdetails.User
            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(openid, "", authorities);

            // 记录远程地址，如果会话已存在（也不会创建会话），还会设置会话ID。
            // 由于 第三方登录（微信扫码）授权时，是从第三方页面跳转的进入本站的，故授权时，没有 getDetails()
            // 第三方登录（微信扫码）授权时，必须进入 WeChatWebsiteHttpServlet
            // detailsWeChatWebsite 是在 WeChatWebsiteHttpServlet 中创建的，并放入 Session 中
            Object detailsWeChatWebsite = session.getAttribute("detailsWeChatWebsite");
            WebAuthenticationDetails details = null;
            if (detailsWeChatWebsite instanceof WebAuthenticationDetails) {
                details = (WebAuthenticationDetails) detailsWeChatWebsite;
            }

            // principal    用户 org.springframework.security.core.userdetails.User
            // credentials  自定义信息、如：WebAuthenticationDetails
            // authorities  权限
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, details, authorities);

            // 保持登录 4 天
            CookieUtils.setSessionCookieTime(request, response, securitySettings.weChatSiteTokenValiditySeconds);

            // 返回验证结果
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (WeixinException e) {
            e.printStackTrace();
        }


        return null;
    }

}
