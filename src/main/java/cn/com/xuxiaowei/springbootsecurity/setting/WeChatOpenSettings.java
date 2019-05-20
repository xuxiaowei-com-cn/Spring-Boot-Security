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
    private String appId;

    /**
     * AppSecret
     */
    private String appSecret;

    /**
     * REDIRECT_URI
     */
    private String redirectUri;

    /**
     * 第三方 网页授权开发 URL
     */
    private String authorizeUrl;

}
