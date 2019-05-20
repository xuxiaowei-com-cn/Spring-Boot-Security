package cn.com.xuxiaowei.springbootsecurity.servlet.login;

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
 * 第三方登录（微信网页（微信内部））URL
 *
 * @author xuxiaowei
 */
public class WeChatWebPageHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        // 微信平台基础支持对象
        Weixin weixin = new Weixin();

        // 网页授权基础支持
        SnsComponent snsComponent = new SnsComponent(weixin);

        // 状态码
        String state = UUID.randomUUID().toString().replace("-", "");

        session.setAttribute("wechat_webpage_connect_state", state);

        // 网页安全授权获取用户信息（获取 Code 页面）
        String oAuth2CodeUserInfoUrl = snsComponent.getOAuth2CodeUrl("http://127.0.0.1/app/wechat/webpage", "snsapi_userinfo", state);

        // 用户同意授权，获取 code ，重定向到微信 URL
        resp.sendRedirect(oAuth2CodeUserInfoUrl);

    }

}
