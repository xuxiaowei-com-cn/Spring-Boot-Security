package cn.com.xuxiaowei.security.config.servlet;

import cn.com.xuxiaowei.security.servlet.login.*;
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

    /**
     * 第三方登录（微信扫码）URL 注册为 Bean
     *
     * @return 可使用 Autowired 的 第三方登录（微信扫码）URL
     */
    @Bean
    WeChatWebsiteHttpServlet weChatWebsiteHttpServletBean() {
        return new WeChatWebsiteHttpServlet();
    }

    /**
     * 第三方登录（微信扫码）URL
     */
    @Bean
    ServletRegistrationBean weChatWebsiteHttpServlet() {
        return new ServletRegistrationBean<>(weChatWebsiteHttpServletBean(), "/wechat/website");
    }

    /**
     * 第三方登录（支付宝）URL 注册为 Bean
     *
     * @return 可使用 Autowired 的 第三方登录（支付宝）URL
     */
    @Bean
    AlipayHttpServlet alipayHttpServletBean() {
        return new AlipayHttpServlet();
    }

    /**
     * 第三方登录（支付宝）URL
     */
    @Bean
    ServletRegistrationBean alipayHttpServlet() {
        return new ServletRegistrationBean<>(alipayHttpServletBean(), "/alipay");
    }

    /**
     * 第三方登录（微博）URL
     */
    @Bean
    ServletRegistrationBean weiBoHttpServlet() {
        return new ServletRegistrationBean<>(new WeiBoHttpServlet(), "/weibo");
    }

}
