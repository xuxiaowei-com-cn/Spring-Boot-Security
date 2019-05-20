package cn.com.xuxiaowei.springbootsecurity.config;

import cn.com.xuxiaowei.springbootsecurity.handlerinterceptor.JsEncryptHandlerInterceptor;
import cn.com.xuxiaowei.springbootsecurity.handlerinterceptor.SmsLoginSuccessHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器 配置
 *
 * @author xuxiaowei
 */
@Configuration
public class HandlerInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        jsEncryptHandlerInterceptor(registry);

        smsLoginSuccessHandlerInterceptor(registry);

    }

    /**
     * 短信登录成功 拦截器 注册为 Bean
     *
     * @return 可使用 Autowired 的 短信登录成功 拦截器
     */
    @Bean
    SmsLoginSuccessHandlerInterceptor smsLoginSuccessHandlerInterceptorBean(){
        return new SmsLoginSuccessHandlerInterceptor();
    }

    /**
     * 短信登录成功 拦截器
     */
    private void smsLoginSuccessHandlerInterceptor(InterceptorRegistry registry) {

        // 拦截URL
        List<String> smsLoginAdd = new ArrayList<>();
        // 排除拦截URL
        List<String> smsLoginExclude = new ArrayList<>();

        smsLoginAdd.add("/");

        registry.addInterceptor(smsLoginSuccessHandlerInterceptorBean())
                .addPathPatterns(smsLoginAdd)
                .excludePathPatterns(smsLoginExclude);

    }

    /**
     * JS 非对称性加密 拦截器
     */
    private void jsEncryptHandlerInterceptor(InterceptorRegistry registry) {

        // 拦截URL
        List<String> jsEncryptAdd = new ArrayList<>();
        // 排除拦截URL
        List<String> jsEncryptExclude = new ArrayList<>();

        // 登录页面 URL
        jsEncryptAdd.add("/login");
        jsEncryptAdd.add("/login/");

        // 注册页面 URL
        jsEncryptAdd.add("/reg");
        jsEncryptAdd.add("/reg/");

        registry.addInterceptor(new JsEncryptHandlerInterceptor())
                .addPathPatterns(jsEncryptAdd)
                .excludePathPatterns(jsEncryptExclude);

    }

}
