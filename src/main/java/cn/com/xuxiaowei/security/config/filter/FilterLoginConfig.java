package cn.com.xuxiaowei.security.config.filter;

import cn.com.xuxiaowei.security.authentication.*;
import cn.com.xuxiaowei.security.filter.login.*;
import cn.com.xuxiaowei.security.setting.SecuritySettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 第三方登录 Filter 配置
 *
 * @author xuxiaowei
 */
@Configuration
public class FilterLoginConfig {

    private final SecuritySettings securitySettings;

    @Autowired
    public FilterLoginConfig(SecuritySettings securitySettings) {
        this.securitySettings = securitySettings;
    }


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

    /**
     * 第三方登录（QQ） 注册为 Bean
     *
     * @return 使用 Autowired 的 Filter
     */
    @Bean
    QqAbstractAuthenticationProcessingFilter qqAbstractAuthenticationProcessingFilter() {
        QqAbstractAuthenticationProcessingFilter qqFilter = new QqAbstractAuthenticationProcessingFilter(securitySettings.qqUrl);
        qqFilter.setAuthenticationManager(new QqAuthenticationManager());
        return qqFilter;
    }

    /**
     * 第三方登录（微信网页（微信内部）） 注册为 Bean
     *
     * @return 使用 Autowired 的 Filter
     */
    @Bean
    WeChatWebPageAbstractAuthenticationProcessingFilter weChatWebPageAbstractAuthenticationProcessingFilter() {
        WeChatWebPageAbstractAuthenticationProcessingFilter weChatWebPageFilter = new WeChatWebPageAbstractAuthenticationProcessingFilter(securitySettings.weChatWebPageUrl);
        weChatWebPageFilter.setAuthenticationManager(new WeChatWebPageAuthenticationManager());
        return weChatWebPageFilter;
    }

    /**
     * 第三方登录（微信扫码） 注册为 Bean
     *
     * @return 使用 Autowired 的 Filter
     */
    @Bean
    WeChatWebsiteAbstractAuthenticationProcessingFilter weChatWebsiteAbstractAuthenticationProcessingFilter() {
        WeChatWebsiteAbstractAuthenticationProcessingFilter weChatWebsiteFilter = new WeChatWebsiteAbstractAuthenticationProcessingFilter(securitySettings.weChatWebsiteUrl);
        weChatWebsiteFilter.setAuthenticationManager(new WeChatWebsiteAuthenticationManager());
        return weChatWebsiteFilter;
    }

    /**
     * 第三方登录（支付宝） 注册为 Bean
     *
     * @return 使用 Autowired 的 Filter
     */
    @Bean
    AlipayAbstractAuthenticationProcessingFilter alipayAbstractAuthenticationProcessingFilter() {
        AlipayAbstractAuthenticationProcessingFilter alipayFilter = new AlipayAbstractAuthenticationProcessingFilter(securitySettings.alipayUrl);
        alipayFilter.setAuthenticationManager(new AlipayAuthenticationManager());
        return alipayFilter;
    }

    /**
     * 第三方登录（微博） 注册为 Bean
     *
     * @return 使用 Autowired 的 Filter
     */
    @Bean
    WeiBoAbstractAuthenticationProcessingFilter weiBoAbstractAuthenticationProcessingFilter() {
        WeiBoAbstractAuthenticationProcessingFilter weibofilter = new WeiBoAbstractAuthenticationProcessingFilter(securitySettings.weiboUrl);
        weibofilter.setAuthenticationManager(new WeiBoAuthenticationManager());
        return weibofilter;
    }

}
