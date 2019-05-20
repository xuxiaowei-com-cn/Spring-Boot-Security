package cn.com.xuxiaowei.springbootsecurity.servlet.login;

import cn.com.xuxiaowei.springbootsecurity.util.security.SecurityUtils;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

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
            // Step1：获取Authorization Code
            String authorizeURL = oauth.getAuthorizeURL(req);

            HttpSession session = req.getSession();

            // 状态码
            String state = (String) session.getAttribute("qq_connect_state");

            // 记录远程地址，如果会话已存在（也不会创建会话），还会设置会话ID。
            // 由于 第三方登录（QQ）授权时，是从第三方页面跳转的进入本站的，故授权时，没有 getDetails()
            // 第三方登录（QQ）授权时，必须进入此方法
            WebAuthenticationDetails details = SecurityUtils.getDetails();

            // 在此方法中，将 getDetails() 放入 Session
            // 授权时，从 Session 中获取
            session.setAttribute("detailsQQ", details);

            resp.sendRedirect(authorizeURL);

        } catch (QQConnectException e) {

            // <code>e.printStackTrace();</code>

            e.printStackTrace();

            log.debug("第三方登录（QQ） 获取登录URL失败！");

        }

    }

}
