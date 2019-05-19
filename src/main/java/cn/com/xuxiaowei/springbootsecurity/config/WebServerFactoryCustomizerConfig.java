package cn.com.xuxiaowei.springbootsecurity.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 自定义错误页面等配置
 * <p>
 * factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
 * <p>
 * {@link WebServerFactoryCustomizer<>} 与 {@link ErrorController} 各有各的特点，根据自己的需要选择
 * <p>
 * 自定义请求转发、重定向：使用 {@link ErrorController}
 * 只能请求转发：使用 {@link WebServerFactoryCustomizer<>}
 * <p>
 * 优先级：{@link WebServerFactoryCustomizer<>} 大于 {@link ErrorController}
 *
 * @author xuxiaowei
 */
@Configuration
public class WebServerFactoryCustomizerConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {

        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/" + HttpStatus.NOT_FOUND.value()));

    }

}
