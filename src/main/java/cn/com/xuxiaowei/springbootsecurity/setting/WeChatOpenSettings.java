package cn.com.xuxiaowei.springbootsecurity.setting;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信开放平台 配置文件读取
 *
 * @author xuxiaowei
 */
@Data
@Component
@ConfigurationProperties(prefix = "weixin.open")
public class WeChatOpenSettings {

    /**
     * AppID
     */
    public String appId;

    /**
     * AppSecret
     */
    public String appSecret;

    /**
     * REDIRECT_URI
     */
    public String redirectUri;

    /**
     * 第三方 网页授权开发 URL
     */
    public String authorizeUrl;

}
