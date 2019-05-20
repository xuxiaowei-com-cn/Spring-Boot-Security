package cn.com.xuxiaowei.springbootsecurity.servlet.login;

import cn.com.xuxiaowei.springbootsecurity.setting.WeChatOpenSettings;
import cn.com.xuxiaowei.springbootsecurity.util.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.weixin4j.Weixin;
import org.weixin4j.component.SnsComponent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * 第三方登录（微信扫码）URL
 *
 * @author xuxiaowei
 */
public class WeChatWebsiteHttpServlet extends HttpServlet {

    @Autowired
    private WeChatOpenSettings weChatOpenSettings;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String appId = weChatOpenSettings.getAppId();
        String appSecret = weChatOpenSettings.getAppSecret();
        String authorizeUrl = weChatOpenSettings.getAuthorizeUrl();
        String redirectUri = weChatOpenSettings.getRedirectUri();

        // 微信平台基础支持对象
        // 区别 1：
        // 网站应用微信登录 使用的是 微信公众平台的 AppID、AppSecret
        Weixin weixin = new Weixin(appId, appSecret);

        // 网页授权基础支持
        // 区别 2：
        // 网站应用微信登录 使用的是 https://open.weixin.qq.com/connect/qrconnect，详见下方链接：
        // https://mp.weixin.qq.com/wiki --> 微信网页开发 --> 微信网页授权
        SnsComponent snsComponent = new SnsComponent(weixin, authorizeUrl);

        String state = UUID.randomUUID().toString().replace("-", "");

        session.setAttribute("wechat_website_connect_state", state);

        // 区别 3：
        // 第三方使用网站应用授权登录前请注意已获取相应网页授权作用域（scope=snsapi_login）
        String oAuth2CodeUrl = snsComponent.getOAuth2CodeUrl(redirectUri, "snsapi_login", state);

        // 记录远程地址，如果会话已存在（也不会创建会话），还会设置会话ID。
        // 由于 第三方登录（微信扫码）授权时，是从第三方页面跳转的进入本站的，故授权时，没有 getDetails()
        // 第三方登录（微信扫码）授权时，必须进入此方法
        WebAuthenticationDetails details = SecurityUtils.getDetails();

        // 在此方法中，将 getDetails() 放入 Session
        // 授权时，从 Session 中获取
        session.setAttribute("detailsWeChatWebsite", details);

        // 重定向到 微信授权扫码登录 页面
        resp.sendRedirect(oAuth2CodeUrl);

    }

}
