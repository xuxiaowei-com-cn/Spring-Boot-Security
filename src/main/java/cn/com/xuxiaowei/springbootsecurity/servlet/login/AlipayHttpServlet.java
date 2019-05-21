package cn.com.xuxiaowei.springbootsecurity.servlet.login;

import cn.com.xuxiaowei.springbootsecurity.setting.AlipaySettings;
import cn.com.xuxiaowei.springbootsecurity.setting.SecuritySettings;
import cn.com.xuxiaowei.springbootsecurity.util.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * 第三方登录（支付宝）URL
 * <p>
 * 文档：https://docs.open.alipay.com/289/105656
 *
 * @author xuxiaowei
 */
public class AlipayHttpServlet extends HttpServlet {

    @Autowired
    private AlipaySettings alipaySettings;

    @Autowired
    private SecuritySettings securitySettings;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String alipayUrl = securitySettings.alipayUrl;

        String domain = alipaySettings.domain;
        String appId = alipaySettings.appId;
        String publicAppAuthorize = alipaySettings.publicAppAuthorize;

        String replacement = domain + alipayUrl;

        String authorizeURL = publicAppAuthorize.replace("APPID", appId).replace("SCOPE", "auth_user")
                .replace("ENCODED_URL", replacement);

        String state = UUID.randomUUID().toString().replace("-", "");

        session.setAttribute("alipay_connect_state", state);

        authorizeURL += "&state=" + state;

        // 记录远程地址，如果会话已存在（也不会创建会话），还会设置会话ID。
        // 由于 第三方登录（支付宝）授权时，是从第三方页面跳转的进入本站的，故授权时，没有 getDetails()
        // 第三方登录（支付宝）授权时，必须进入此方法
        WebAuthenticationDetails details = SecurityUtils.getDetails();

        // 在此方法中，将 getDetails() 放入 Session
        // 授权时，从 Session 中获取
        session.setAttribute("detailsAlipay", details);

        resp.sendRedirect(authorizeURL);

    }

}
