package cn.com.xuxiaowei.springbootsecurity.filter.login;

import cn.com.xuxiaowei.springbootsecurity.entity.Qq;
import cn.com.xuxiaowei.springbootsecurity.service.IQqService;
import cn.com.xuxiaowei.springbootsecurity.util.security.SecurityUtils;
import com.alibaba.fastjson.JSON;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.Avatar;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
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
 * 第三方登录（QQ）认证处理过滤器
 * <p>
 * 关于保存 AccessToken 的说明：
 * 保存 AccessToken 和 ExpireIn（过期时间）
 * 使用 AccessToken、OpenID、app_ID 获取 User 信息参见：
 * cn.com.xuxiaowei.springbootsecurity.SpringBootSecurityApplicationTests 中的 getQQInfoByAccessTokenAndOpenID() 方法
 *
 * @author xuxiaowei
 */
@Slf4j
public class QqAbstractAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private IQqService qqService;

    public QqAbstractAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        HttpSession session = request.getSession();

        // 获取 Session 中的 Authorization Code URL 状态码
        String qqConnectState = (String) session.getAttribute("qq_connect_state");

        // 接收 状态码
        String state = request.getParameter("state");

        if (StringUtils.isEmpty(qqConnectState)) {
            map.put("msg", "QQ登录受到攻击！");
            data.put("details", "Session 中不存在 QQ 登录的状态码！");
            log.debug(map.toString());
            response(response, map);
            return null;
        }

        if (StringUtils.isEmpty(state)) {
            map.put("msg", "QQ登录受到攻击！");
            data.put("details", "未收到 QQ 登录的状态码！");
            log.debug(map.toString());
            response(response, map);
            return null;
        }

        if (!state.equals(qqConnectState)) {
            map.put("msg", "QQ登录受到攻击！");
            data.put("details", "Session 中的 QQ 登录的状态码与接收的不同！");
            log.debug(map.toString());
            response(response, map);
            return null;
        }

        // QQ 授权基础类
        Oauth oauth = new Oauth();

        try {

            // 获取 AccessToken Object
            // 使用的 配置文件名：accessTokenURL
            // 使用的 接口URL：https://graph.qq.com/oauth2.0/token
            // 官方文档：http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
            // Step2：通过Authorization Code获取Access Token
            AccessToken accessTokenObj = oauth.getAccessTokenByRequest(request);

            log.debug("");
            log.debug(accessTokenObj.toString());
            log.debug("");

            // 获取 accessToken
            String accessToken = accessTokenObj.getAccessToken();

            if (StringUtils.isEmpty(accessToken)) {
                map.put("msg", "QQ登录的code已过期，请重新登录！");
                data.put("details", "QQ登录的code只能使用一次，重复使用时，调用接口失败！");
                log.debug(map.toString());
                response(response, map);
                return null;
            }

            // 获取 OpenID Object
            // 使用的 配置文件名：getOpenIDURL
            // 使用的 接口URL：https://graph.qq.com/oauth2.0/me
            // 官方文档：http://wiki.connect.qq.com/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7openid_oauth2-0
            // 获取用户OpenID_OAuth2.0
            OpenID openID = new OpenID(accessToken);

            // 获取 QQ OpenID
            String userOpenID = openID.getUserOpenID();

            // 根据 openId，查询 QQ
            Qq qq = qqService.getOpenId(userOpenID);

            // 获取现在的时间
            LocalDateTime now = LocalDateTime.now();

            // 获取 Access Token 有效时间（秒）
            long expireIn = accessTokenObj.getExpireIn();

            // 获取 Access Token 过期时间
            LocalDateTime accessTokenExpiredDate = now.plus(expireIn, ChronoUnit.SECONDS);

            // 该 QQ 从未登录过
            if (qq == null) {

                // 获取 用户 userInfo（com.qq.connect.api.qzone.UserInfo）
                // 使用的 配置文件名：getUserInfoURL
                // 使用的 接口URL：https://graph.qq.com/user/get_user_info
                // 官方文档：http://wiki.connect.qq.com/openapi%E8%B0%83%E7%94%A8%E8%AF%B4%E6%98%8E_oauth2-0
                // OpenAPI调用说明_OAuth2.0
                UserInfo userInfo = new UserInfo(accessToken, userOpenID);

                // 获取 用户 userInfoBean（com.qq.connect.javabeans.qzone.UserInfoBean） QQ 详细信息
                UserInfoBean userInfoBean = userInfo.getUserInfo();

                log.debug("");
                log.debug(userInfoBean.toString());
                log.debug("");

                // 创建 QQ 实体类，用于 强制类型转换
                // 强制类型转换为空时，会出现异常
                qq = new Qq();

                // 强制类型转换 org.springframework.beans.BeanUtils
                BeanUtils.copyProperties(userInfoBean, qq);

                // 放入 QQ OpenID
                qq.setOpenId(userOpenID);

                // 头像 对象
                Avatar avatar = userInfoBean.getAvatar();

                // 头像需要手动放入 QQ 中
                qq.setFigureurl30(avatar.getAvatarURL30());
                qq.setFigureurl50(avatar.getAvatarURL50());
                qq.setFigureurl100(avatar.getAvatarURL50());

                // 设置 Access Token
                qq.setAccessToken(accessToken);

                // 设置 Access Token 过期时间
                qq.setAccessTokenExpiredDate(accessTokenExpiredDate);

                log.debug("");
                log.debug(qq.toString());
                log.debug("");

                // 保存 QQ 数据
                boolean save = qqService.save(qq);

                // 保存成功
                if (save) {
                    log.debug("QQ 数据保存成功：" + qq);
                } else {
                    // 保存失败
                    log.debug("QQ 数据保存失败：" + qq);
                }

            } else {

                // 数据库中的 QQ 数据，设置新的 Access Token
                qq.setAccessToken(accessToken);
                // 数据库中的 QQ 数据，设置新的 Access Token 过期时间
                qq.setAccessTokenExpiredDate(accessTokenExpiredDate);

                boolean updateById = qqService.updateById(qq);

                if (updateById) {
                    log.debug("QQ 数据更新 Access Token 及过期时间成功：" + qq);
                } else {
                    log.debug("QQ 数据更新 Access Token 及过期时间失败：" + qq);
                }

            }


            List<GrantedAuthority> authorities = new ArrayList<>();

            // 给一个权限
            authorities.add(new SimpleGrantedAuthority("ROLE_QQ"));

            // 注意此时的类型 org.springframework.security.core.userdetails.User
            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(userOpenID, accessToken, authorities);

            // 记录远程地址，如果会话已存在（也不会创建会话），还会设置会话ID。
            WebAuthenticationDetails details = SecurityUtils.getDetails();

            // principal    用户 org.springframework.security.core.userdetails.User
            // credentials  自定义信息、如：WebAuthenticationDetails
            // authorities  权限
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, details, authorities);

            // 返回验证结果
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (QQConnectException e) {
            e.printStackTrace();
        }

        return null;
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
