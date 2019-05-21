package cn.com.xuxiaowei.springbootsecurity.filter.login;

import cn.com.xuxiaowei.springbootsecurity.entity.WeiBo;
import cn.com.xuxiaowei.springbootsecurity.service.IWeiBoService;
import cn.com.xuxiaowei.springbootsecurity.setting.SecuritySettings;
import cn.com.xuxiaowei.springbootsecurity.util.cookie.CookieUtils;
import cn.com.xuxiaowei.springbootsecurity.util.response.ResponseUtils;
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
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

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
 * 第三方登录（微博）认证处理过滤器
 *
 * @author xuxiaowei
 */
@Slf4j
public class WeiBoAbstractAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private SecuritySettings securitySettings;

    @Autowired
    private IWeiBoService weiBoService;

    public WeiBoAbstractAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        HttpSession session = request.getSession();

        // 获取 Session 中的 Authorization Code URL 状态码
        String weiboConnectState = (String) session.getAttribute("weibo_connect_state");

        // 接收 状态码
        String state = request.getParameter("state");

        // code
        String code = request.getParameter("code");

        if (StringUtils.isEmpty(weiboConnectState)) {
            map.put("msg", "微博登录受到攻击！");
            data.put("details", "Session 中不存在 微博 登录的状态码！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        if (StringUtils.isEmpty(state)) {
            map.put("msg", "微博登录受到攻击！");
            data.put("details", "未收到 微博 登录的状态码！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        if (StringUtils.isEmpty(code)) {
            map.put("msg", "微博登录受到攻击！");
            data.put("details", "未收到 微博 登录的 code！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        if (!state.equals(weiboConnectState)) {
            map.put("msg", "微博登录受到攻击！");
            data.put("details", "Session 中的 微博 登录的状态码与接收的不同！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        // 微博 授权基础类
        Oauth oauth = new Oauth();

        try {

            // 获取 AccessToken Object
            // 使用的 配置文件名：accessTokenURL
            // 使用的 接口URL：https://api.weibo.com/oauth2/access_token
            // 官方文档：https://open.weibo.com/wiki/Connect/login
            // 换取Access Token
            AccessToken accessTokenObj = oauth.getAccessTokenByCode(code);

            // 微博 UID
            String uid = accessTokenObj.getUid();

            // 访问令牌。通过该令牌调用需要授权类接口
            String accessToken = accessTokenObj.getAccessToken();

            // 访问令牌的有效时间，单位是秒。
            int expireIn = Integer.parseInt(accessTokenObj.getExpireIn());

            // 刷新令牌。通过该令牌可以刷新access_token
            String refreshToken = accessTokenObj.getRefreshToken();

            // 获取现在的时间
            LocalDateTime now = LocalDateTime.now();

            // 获取 Access Token 过期时间
            LocalDateTime accessTokenExpiredDate = now.plus(expireIn, ChronoUnit.SECONDS);

            WeiBo weiBo = weiBoService.getId(uid);

            if (weiBo == null) {

                Users um = new Users(accessToken);

                // 根据用户ID获取用户信息
                // 使用的 配置文件名：baseURL
                // 使用的 接口URL：https://api.weibo.com/2/users/show.json
                // 官方文档：https://open.weibo.com/wiki/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF
                // 获取用户基本信息
                User userInfo = um.showUserById(uid);

                log.debug("");
                log.debug(userInfo.toString());
                log.debug("");

                // 创建 QQ 实体类，用于 强制类型转换
                // 强制类型转换为空时，会出现异常
                weiBo = new WeiBo();

                // 强制类型转换 org.springframework.beans.BeanUtils
                BeanUtils.copyProperties(userInfo, weiBo);

                // 设置 访问令牌。通过该令牌调用需要授权类接口
                weiBo.setAccessToken(accessToken);

                // 设置 访问令牌过期时间
                weiBo.setAccessTokenExpiredDate(accessTokenExpiredDate);

                // 设置 刷新令牌。通过该令牌可以刷新access_token
                weiBo.setRefreshToken(refreshToken);

                log.debug("");
                log.debug(weiBo.toString());
                log.debug("");

                boolean save = weiBoService.save(weiBo);

                // 保存成功
                if (save) {
                    log.debug("微博 数据保存成功：" + weiBo);
                } else {
                    // 保存失败
                    log.debug("微博 数据保存失败：" + weiBo);
                }

            } else {

                // 设置 访问令牌。通过该令牌调用需要授权类接口
                weiBo.setAccessToken(accessToken);

                // 设置 访问令牌过期时间
                weiBo.setAccessTokenExpiredDate(accessTokenExpiredDate);

                // 设置 刷新令牌。通过该令牌可以刷新access_token
                weiBo.setRefreshToken(refreshToken);

                boolean updateById = weiBoService.updateById(weiBo);

                if (updateById) {
                    log.debug("微博 数据更新 Access Token 及过期时间成功：" + weiBo);
                } else {
                    log.debug("微博 数据更新 Access Token 及过期时间失败：" + weiBo);
                }

            }

            List<GrantedAuthority> authorities = new ArrayList<>();

            // 给一个权限
            authorities.add(new SimpleGrantedAuthority("ROLE_WEIBO"));

            // 注意此时的类型 org.springframework.security.core.userdetails.User
            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(uid, accessToken, authorities);

            // 记录远程地址，如果会话已存在（也不会创建会话），还会设置会话ID。
            // 由于 第三方登录（微博）授权时，是从第三方页面跳转的进入本站的，故授权时，没有 getDetails()
            // 第三方登录（微博）授权时，必须进入 WeiBoHttpServlet
            // detailsWeiBo 是在 WeiBoHttpServlet 中创建的，并放入 Session 中
            Object detailsWeiBo = session.getAttribute("detailsWeiBo");
            WebAuthenticationDetails details = null;
            if (detailsWeiBo instanceof WebAuthenticationDetails) {
                details = (WebAuthenticationDetails) detailsWeiBo;
            }

            // principal    用户 org.springframework.security.core.userdetails.User
            // credentials  自定义信息、如：WebAuthenticationDetails
            // authorities  权限
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, details, authorities);

            // 保持登录 6 天
            CookieUtils.setSessionCookieTime(request, response, securitySettings.weiBoTokenValiditySeconds);

            // 返回验证结果
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (WeiboException e) {
            e.printStackTrace();
        }

        return null;
    }

}
