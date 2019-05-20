package cn.com.xuxiaowei.springbootsecurity.config;

import cn.com.xuxiaowei.springbootsecurity.servlet.login.QqHttpServlet;
import cn.com.xuxiaowei.springbootsecurity.servlet.login.WeChatWebPageHttpServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Servlet 登录 配置
 *
 * @author xuxiaowei
 */
@Configuration
public class ServletLoginConfig {

    /**
     * 第三方登录（QQ） HttpServlet URL
     */
    @Bean
    ServletRegistrationBean qqHttpServlet() {
        return new ServletRegistrationBean<>(new QqHttpServlet(), "/qq");
    }

    /**
     * 第三方登录（微信网页（微信内部））URL 注册为 Bean
     *
     * @return 可使用 Autowired 的 第三方登录（微信网页（微信内部））URL
     */
    @Bean
    WeChatWebPageHttpServlet weChatWebPageHttpServletBean() {
        return new WeChatWebPageHttpServlet();
    }

    /**
     * 第三方登录（微信网页（微信内部））URL
     */
    @Bean
    ServletRegistrationBean weChatWebPageHttpServlet() {
        return new ServletRegistrationBean<>(weChatWebPageHttpServletBean(), "/wechat/webpage");
    }

}
