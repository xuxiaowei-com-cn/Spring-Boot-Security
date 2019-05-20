package cn.com.xuxiaowei.springbootsecurity.setting;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Security 自定义配置
 *
 * @author xuxiaowei
 */
@Data
@Component
@ConfigurationProperties(prefix = "security")
public class SecuritySettings {

    /**
     * 定制登录页面的访问 URL
     */
    public String loginPage;

    /**
     * 定制登录请求的访问 URL
     */
    public String loginProcessingUrl;

    /**
     * 定制登录成功后转向的 URL
     */
    public String defaultSuccessUrl;

    /**
     * 定制注销 URL（行为）
     */
    public String logoutUrl;

    /**
     * 定制登录失败后转向的 URL
     */
    public String failureForwardUrl;

    /**
     * 登录时图片验证码验证失败 URL
     */
    public String failurePatchcaUrl;

    /**
     * 注销成功后跳转的URL
     */
    public String logoutSuccessUrl;

    /**
     * remember-me
     */
    public String rememberMeParameter;

    /**
     * remember-me key 指定 Cookie 中的私钥
     */
    public String key;

    /**
     * 指定 Cookie 有效时间
     * <p>
     * 60 * 60 * 24 * 14 为 1209600 秒，即 2 个星期
     */
    public int tokenValiditySeconds;

    /**
     * QQ 登录 URL
     */
    public String qqUrl;

}
