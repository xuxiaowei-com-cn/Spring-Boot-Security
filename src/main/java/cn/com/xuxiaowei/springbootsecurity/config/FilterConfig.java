package cn.com.xuxiaowei.springbootsecurity.config;

import cn.com.xuxiaowei.springbootsecurity.authentication.SmsWebsiteAuthenticationManager;
import cn.com.xuxiaowei.springbootsecurity.filter.login.SmsAbstractAuthenticationProcessingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Filter 配置
 *
 * @author xuxiaowei
 */
@Configuration
public class FilterConfig {

    /**
     * 短信验证码登录 认证处理过滤器 注册为 Bean
     *
     * @return 可使用 Autowired 的 短信验证码登录 认证处理过滤器
     */
    @Bean
    SmsAbstractAuthenticationProcessingFilter smsAbstractAuthenticationProcessingFilterBean() {
        SmsAbstractAuthenticationProcessingFilter smsAbstractAuthenticationProcessingFilter = new SmsAbstractAuthenticationProcessingFilter("/sms/login.do");
        smsAbstractAuthenticationProcessingFilter.setAuthenticationManager(new SmsWebsiteAuthenticationManager());
        return smsAbstractAuthenticationProcessingFilter;
    }

    /**
     * 短信验证码登录
     */
    @Bean
    public FilterRegistrationBean smsAbstractAuthenticationProcessingFilter() {
        FilterRegistrationBean<SmsAbstractAuthenticationProcessingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(smsAbstractAuthenticationProcessingFilterBean());
        return registration;
    }

}
