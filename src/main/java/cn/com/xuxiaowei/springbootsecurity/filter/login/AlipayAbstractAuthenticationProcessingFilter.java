package cn.com.xuxiaowei.springbootsecurity.filter.login;

import cn.com.xuxiaowei.springbootsecurity.entity.Alipay;
import cn.com.xuxiaowei.springbootsecurity.service.IAlipayService;
import cn.com.xuxiaowei.springbootsecurity.setting.AlipaySettings;
import cn.com.xuxiaowei.springbootsecurity.setting.SecuritySettings;
import cn.com.xuxiaowei.springbootsecurity.util.cookie.CookieUtils;
import cn.com.xuxiaowei.springbootsecurity.util.response.ResponseUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
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
 * 第三方登录（支付宝）认证处理过滤器
 * <p>
 * 文档：https://docs.open.alipay.com/289/105656
 *
 * @author xuxiaowei
 */
@Slf4j
public class AlipayAbstractAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private SecuritySettings securitySettings;

    @Autowired
    private AlipaySettings alipaySettings;

    @Autowired
    private IAlipayService alipayService;

    public AlipayAbstractAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        Map<String, Object> map = new HashMap<>(4);
        Map<String, Object> data = new HashMap<>(4);
        map.put("data", data);

        HttpSession session = request.getSession();

        String authCode = request.getParameter("auth_code");

        String state = request.getParameter("state");

        String alipayConnectState = (String) session.getAttribute("alipay_connect_state");

        if (StringUtils.isEmpty(alipayConnectState)) {
            map.put("msg", "支付宝登录受到攻击！");
            data.put("details", "Session 中不存在 支付宝 登录的状态码！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        if (StringUtils.isEmpty(state)) {
            map.put("msg", "支付宝登录受到攻击！");
            data.put("details", "未收到 支付宝 登录的状态码！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        if (StringUtils.isEmpty(authCode)) {
            map.put("msg", "支付宝登录受到攻击！");
            data.put("details", "未收到 支付宝 登录的 auth_code！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }

        if (!state.equals(alipayConnectState)) {
            map.put("msg", "支付宝登录受到攻击！");
            data.put("details", "Session 中的 支付宝 登录的状态码与接收的不同！");
            log.debug(map.toString());
            ResponseUtils.response(response, map);
            return null;
        }


        String appId = alipaySettings.appId;
        // application.yml 中的 alipay.app-private-key 是直接复制生成的，有换行，在这里去空格
        String appPrivateKey = alipaySettings.appPrivateKey.replace(" ", "");
        String alipayPublicKey = alipaySettings.alipayPublicKey;
        String charset = alipaySettings.charset;
        String url = alipaySettings.url;
        String format = alipaySettings.format;
        String signType = alipaySettings.signType;


        // 蚂蚁金服 授权基础类
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, appPrivateKey, format, charset, alipayPublicKey, signType);

        // 蚂蚁金服 系统授权 Token 请求 Object
        AlipaySystemOauthTokenRequest alipaySystemOauthTokenRequest = new AlipaySystemOauthTokenRequest();

        // 授权码，用户对应用授权后得到。
        alipaySystemOauthTokenRequest.setCode(authCode);

        // 值为authorization_code时，代表用code换取；值为refresh_token时，代表用refresh_token换取
        alipaySystemOauthTokenRequest.setGrantType("authorization_code");


        try {

            // 蚂蚁金服 系统授权 Token 响应 Object
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(alipaySystemOauthTokenRequest);

            // 支付宝用户的唯一userId
            String userId = oauthTokenResponse.getUserId();

            // 访问令牌。通过该令牌调用需要授权类接口
            String accessToken = oauthTokenResponse.getAccessToken();

            // 访问令牌的有效时间，单位是秒。
            int expiresIn = Integer.parseInt(oauthTokenResponse.getExpiresIn());

            // 刷新令牌。通过该令牌可以刷新access_token
            String refreshToken = oauthTokenResponse.getRefreshToken();

            // 刷新令牌的有效时间，单位是秒。
            int reExpiresIn = Integer.parseInt(oauthTokenResponse.getReExpiresIn());

            // 由于 com.alipay.api.response.AlipaySystemOauthTokenResponse 没有重写 toString ,所以将其转化为 JSON
            Object oauthTokenResponseJson = JSON.toJSON(oauthTokenResponse);

            log.debug(oauthTokenResponseJson.toString());

            // 根据 userId（支付宝），查询 Alipay
            Alipay alipay = alipayService.getUserId(userId);

            LocalDateTime now = LocalDateTime.now();

            // AccessToken 过期时间
            LocalDateTime accessTokenExpiredDate = now.plus(expiresIn, ChronoUnit.SECONDS);

            // RefreshToken 过期时间
            LocalDateTime refreshTokenExpiredDate = now.plus(reExpiresIn, ChronoUnit.SECONDS);

            if (alipay == null) {

                // 蚂蚁金服 用户信息 分享 请求 Object
                AlipayUserInfoShareRequest alipayUserInfoShareRequest = new AlipayUserInfoShareRequest();

                // 蚂蚁金服 用户信息 分享 响应 Object
                AlipayUserInfoShareResponse alipayUserInfoShareResponse = alipayClient.execute(alipayUserInfoShareRequest, accessToken);

                if (alipayUserInfoShareResponse.isSuccess()) {

                    log.debug("调用成功：" + ReflectionToStringBuilder.toString(alipayUserInfoShareResponse));

                    // 创建 Alipay 实体类，用于 强制类型转换
                    // 强制类型转换为空时，会出现异常
                    alipay = new Alipay();

                    // 强制类型转换 org.springframework.beans.BeanUtils
                    BeanUtils.copyProperties(alipayUserInfoShareResponse, alipay);

                    // 设置 访问令牌。通过该令牌调用需要授权类接口
                    alipay.setAccessToken(accessToken);

                    // 设置 访问令牌过期时间
                    alipay.setAccessTokenExpiredDate(accessTokenExpiredDate);

                    // 设置 刷新令牌。通过该令牌可以刷新access_token
                    alipay.setRefreshToken(refreshToken);

                    // 设置 刷新令牌过期时间
                    alipay.setRefreshTokenExpiredDate(refreshTokenExpiredDate);

                    boolean save = alipayService.save(alipay);

                    // 保存成功
                    if (save) {
                        log.debug("支付宝 用户数据保存成功：" + alipay);
                    } else {
                        // 保存失败
                        log.debug("支付宝 用户数据保存失败：" + alipay);
                    }

                } else {

                    log.debug("调用失败：");

                }


            } else {

                // 更新 访问令牌。通过该令牌调用需要授权类接口
                alipay.setAccessToken(accessToken);

                // 更新 访问令牌过期时间
                alipay.setAccessTokenExpiredDate(accessTokenExpiredDate);

                // 更新 刷新令牌。通过该令牌可以刷新access_token
                alipay.setRefreshToken(refreshToken);

                // 更新 刷新令牌过期时间
                alipay.setRefreshTokenExpiredDate(refreshTokenExpiredDate);

                boolean updateById = alipayService.updateById(alipay);

                if (updateById) {
                    log.debug("支付宝 用户数据更新 Access Token、Refresh Token 及过期时间成功：" + alipay);
                } else {
                    log.debug("支付宝 用户数据更新 Access Token、Refresh Token 及过期时间失败：" + alipay);
                }

            }


            List<GrantedAuthority> authorities = new ArrayList<>();

            // 给一个权限
            authorities.add(new SimpleGrantedAuthority("ROLE_ALIPAY"));

            // 注意此时的类型 org.springframework.security.core.userdetails.User
            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(userId, accessToken, authorities);

            // 记录远程地址，如果会话已存在（也不会创建会话），还会设置会话ID。
            // 由于 第三方登录（支付宝）授权时，是从第三方页面跳转的进入本站的，故授权时，没有 getDetails()
            // 第三方登录（支付宝）授权时，必须进入 AlipayHttpServlet
            // detailsAlipay 是在 AlipayHttpServlet 中创建的，并放入 Session 中
            Object detailsAlipay = session.getAttribute("detailsAlipay");
            WebAuthenticationDetails details = null;
            if (detailsAlipay instanceof WebAuthenticationDetails) {
                details = (WebAuthenticationDetails) detailsAlipay;
            }

            // principal    用户 org.springframework.security.core.userdetails.User
            // credentials  自定义信息、如：WebAuthenticationDetails
            // authorities  权限
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, details, authorities);

            // 保持登录 5 天
            CookieUtils.setSessionCookieTime(request, response, securitySettings.alipayTokenValiditySeconds);

            // 返回验证结果
            return this.getAuthenticationManager().authenticate(authRequest);


        } catch (AlipayApiException e) {
            e.printStackTrace();
        }


        return null;
    }

}
