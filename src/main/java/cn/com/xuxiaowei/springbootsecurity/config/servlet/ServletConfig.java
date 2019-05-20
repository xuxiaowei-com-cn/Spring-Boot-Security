package cn.com.xuxiaowei.springbootsecurity.config.servlet;

import cn.com.xuxiaowei.springbootsecurity.servlet.PatchcaHttpServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Servlet 配置
 *
 * @author xuxiaowei
 */
@Configuration
public class ServletConfig {

    /**
     * 将 PatchcaHttpServlet 注册为 Bean
     *
     * @return 可使用 @Autowired 的 Servlet
     */
    @Bean
    PatchcaHttpServlet patchcaHttpServletBean() {
        return new PatchcaHttpServlet();
    }

    /**
     * GitHub 图片验证码 Patchca URL
     */
    @Bean
    ServletRegistrationBean patchcaHttpServlet() {
        return new ServletRegistrationBean<>(patchcaHttpServletBean(), "/patchca");
    }

}
