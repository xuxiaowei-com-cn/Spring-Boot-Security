package cn.com.xuxiaowei.springbootsecurity.servlet.login;

import cn.com.xuxiaowei.springbootsecurity.util.security.SecurityUtils;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import weibo4j.Oauth;
import weibo4j.model.WeiboException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * 第三方登录（微博）URL
 *
 * @author xuxiaowei
 */
public class WeiBoHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 微博 授权基础类
        Oauth oauth = new Oauth();

        try {

            HttpSession session = req.getSession();

            // 状态码
            String state = UUID.randomUUID().toString().replace("-", "");

            session.setAttribute("weibo_connect_state", state);

            // 登录授权页面 URL

            // 获取 Authorization Code URL
            // 使用的 配置文件名：authorizeURL
            // 使用的 接口URL：https://api.weibo.com/oauth2/authorize
            // 官方文档：https://open.weibo.com/wiki/Connect/login
            // Web端的验证授权
            String authorizeURL = oauth.authorize("code", state);

            // 记录远程地址，如果会话已存在（也不会创建会话），还会设置会话ID。
            // 由于 第三方登录（微博）授权时，是从第三方页面跳转的进入本站的，故授权时，没有 getDetails()
            // 第三方登录（微博）授权时，必须进入此方法
            WebAuthenticationDetails details = SecurityUtils.getDetails();

            // 在此方法中，将 getDetails() 放入 Session
            // 授权时，从 Session 中获取
            session.setAttribute("detailsWeiBo", details);

            resp.sendRedirect(authorizeURL);

        } catch (WeiboException e) {
            e.printStackTrace();
        }

    }

}
