package cn.com.xuxiaowei.springbootsecurity.config;

import cn.com.xuxiaowei.springbootsecurity.authentication.QqAuthenticationManager;
import cn.com.xuxiaowei.springbootsecurity.authentication.WeChatWebPageAuthenticationManager;
import cn.com.xuxiaowei.springbootsecurity.authentication.WeChatWebsiteAuthenticationManager;
import cn.com.xuxiaowei.springbootsecurity.filter.login.BeforeLoginPatchcaHttpFilter;
import cn.com.xuxiaowei.springbootsecurity.filter.login.QqAbstractAuthenticationProcessingFilter;
import cn.com.xuxiaowei.springbootsecurity.filter.login.WeChatWebPageAbstractAuthenticationProcessingFilter;
import cn.com.xuxiaowei.springbootsecurity.filter.login.WeChatWebsiteAbstractAuthenticationProcessingFilter;
import cn.com.xuxiaowei.springbootsecurity.service.IUserService;
import cn.com.xuxiaowei.springbootsecurity.service.impl.UserDetailsServiceImpl;
import cn.com.xuxiaowei.springbootsecurity.setting.SecuritySettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 安全控制 配置
 *
 * @author xuxiaowei
 */
@Configuration
public class WebSecurityConfigurerAdapterConfig extends WebSecurityConfigurerAdapter {

    private final SecuritySettings securitySettings;
    private final IUserService userService;

    @Autowired
    public WebSecurityConfigurerAdapterConfig(SecuritySettings securitySettings, IUserService userService) {
        this.securitySettings = securitySettings;
        this.userService = userService;
    }

    /**
     * 授权登录（用户名密码登录）前 验证图片验证码 注册为 bean
     *
     * @return 可使用 Autowired 的 授权登录（用户名密码登录）前 验证图片验证码
     */
    @Bean
    BeforeLoginPatchcaHttpFilter beforeLoginPatchcaHttpFilter() {
        return new BeforeLoginPatchcaHttpFilter();
    }

    /**
     * 第三方登录（QQ）登录 注册为 Bean
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
     * 第三方（微信扫码） 注册为 Bean
     *
     * @return 使用 Autowired 的 Filter
     */
    @Bean
    WeChatWebsiteAbstractAuthenticationProcessingFilter weChatWebsiteAbstractAuthenticationProcessingFilter() {
        WeChatWebsiteAbstractAuthenticationProcessingFilter weChatWebsiteFilter = new WeChatWebsiteAbstractAuthenticationProcessingFilter(securitySettings.weChatWebsiteUrl);
        weChatWebsiteFilter.setAuthenticationManager(new WeChatWebsiteAuthenticationManager());
        return weChatWebsiteFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 授权登录（用户名密码登录）前 验证图片验证码
        http.addFilterBefore(beforeLoginPatchcaHttpFilter(), UsernamePasswordAuthenticationFilter.class);

        // 第三方登录（QQ）
        // 已将 QqAbstractAuthenticationProcessingFilter 注册为 Bean，可忽略本行代码
        http.addFilterAt(qqAbstractAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

        // 第三方登录（微信网页（微信内部））
        // 已将 WeChatWebPageAbstractAuthenticationProcessingFilter 注册为 Bean，可忽略本行代码
        http.addFilterAt(weChatWebPageAbstractAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

        // 第三方登录（微信扫码）
        // 已将 WeChatWebPageAbstractAuthenticationProcessingFilter 注册为 Bean，可忽略本行代码
        http.addFilterAt(weChatWebsiteAbstractAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

        // 指定支持基于表单的身份验证。
        http.formLogin()
                // 定制登录页面的访问 URL
                // 优先级 低于 <code>http.exceptionHandling().authenticationEntryPoint();</code>
                .loginPage(securitySettings.loginPage)
                // 定制登录请求的访问 URL
                .loginProcessingUrl(securitySettings.loginProcessingUrl)
                // 定制登录失败后转向的 URL
                .failureForwardUrl(securitySettings.failureForwardUrl)
                // 定制登录成功后转向的 URL
                // 如果用户在进行身份验证之前未访问过安全页面，则指定用户在成功进行身份验证后的位置。
                .defaultSuccessUrl(securitySettings.defaultSuccessUrl, true)
                .permitAll()
        ;

        // 优先级 高于 <code>http.formLogin().loginPage()</code>
        // 使用此配置的目的是，记录需要授权登录前的 URL 等操作，定制自己的行为
        http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPointConfig(securitySettings.loginPage));

        // 使用 logout 方法定制注销行为
        http.logout()
                // 定制注销 URL（行为）
                // logoutUrl 指定注销 URL 路径
                .logoutUrl(securitySettings.logoutUrl)
                // 注销成功后跳转的URL
                .logoutSuccessUrl(securitySettings.logoutSuccessUrl)
                .permitAll();

        // 开启 Cookie 储存用户信息
        http.rememberMe()
                // tokenValiditySeconds 指定 Cookie 有效时间
                .tokenValiditySeconds(securitySettings.tokenValiditySeconds)
                // remember-me
                .rememberMeParameter(securitySettings.rememberMeParameter)
                // key 指定 Cookie 中的私钥
                .key(securitySettings.key)
        ;

        // 指定URL不需要CSRF保护
        http.csrf().ignoringAntMatchers("/csrf/**");

        // 测试权限
        // 至少存在一个，否则会出现错误
        http.authorizeRequests().antMatchers("/test").hasRole("USER");

    }

    /**
     * 密码编码器 注册为 Bean
     */
    @Bean
    WebSecurityPasswordEncoderConfig webSecurityPasswordEncoderConfig() {
        return new WebSecurityPasswordEncoderConfig();
    }

    /**
     * 加载用户特定数据的核心接口实现类注册为 Bean。
     */
    @Bean
    UserDetailsService customUserServiceImpl() {
        return new UserDetailsServiceImpl(userService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserServiceImpl()).passwordEncoder(webSecurityPasswordEncoderConfig());

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

}
