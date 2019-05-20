package cn.com.xuxiaowei.springbootsecurity.servlet.login;

import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 第三方登录（QQ） HttpServlet
 *
 * @author xuxiaowei
 */
@Slf4j
public class QqHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // QQ 授权基础类
        Oauth oauth = new Oauth();

        try {

            // 获取 Authorization Code URL
            // 使用的 配置文件名：authorizeURL
            // 使用的 接口URL：https://graph.qq.com/oauth2.0/authorize
            // 官方文档：http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
            String authorizeURL = oauth.getAuthorizeURL(req);

            HttpSession session = req.getSession();

            // 状态码
            String state = (String) session.getAttribute("qq_connect_state");

            resp.sendRedirect(authorizeURL);

        } catch (QQConnectException e) {

            // <code>e.printStackTrace();</code>

            e.printStackTrace();

            log.debug("第三方登录（QQ） 获取登录URL失败！");

        }

    }

}
