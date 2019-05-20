package cn.com.xuxiaowei.springbootsecurity.config.security;

import cn.com.xuxiaowei.springbootsecurity.config.LoginUrlAuthenticationEntryPointConfig;
import cn.com.xuxiaowei.springbootsecurity.filter.login.BeforeLoginPatchcaHttpFilter;
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


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 授权登录（用户名密码登录）前 验证图片验证码
        http.addFilterBefore(beforeLoginPatchcaHttpFilter(), UsernamePasswordAuthenticationFilter.class);

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
