package cn.com.xuxiaowei.springbootsecurity.config;

import cn.com.xuxiaowei.springbootsecurity.servlet.login.QqHttpServlet;
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

}
