package cn.com.xuxiaowei.springbootsecurity.servlet.login;

import cn.com.xuxiaowei.springbootsecurity.setting.AlipaySettings;
import cn.com.xuxiaowei.springbootsecurity.setting.SecuritySettings;
import org.springframework.beans.factory.annotation.Autowired;

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

        resp.sendRedirect(authorizeURL);

    }

}
