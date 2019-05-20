package cn.com.xuxiaowei.springbootsecurity.setting;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付宝 配置
 *
 * @author xuxiaowei
 */
@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipaySettings {

    /**
     * 域名
     */
    private String domain;

    /**
     * 支付宝网关（固定）
     * <p>
     * https://openapi.alipay.com/gateway.do
     */
    private String url;

    /**
     * APPID 即创建应用后生成
     */
    private String appId;

    /**
     * 开发者私钥，由开发者自己生成
     */
    private String appPrivateKey;

    /**
     * 参数返回格式，只支持json
     * <p>
     * json（固定）
     */
    private String format;

    /**
     * 编码集，支持GBK/UTF-8
     */
    private String charset;

    /**
     * 支付宝公钥，由支付宝生成
     */
    private String alipayPublicKey;

    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     * <p>
     * RSA2
     */
    private String signType;

    /**
     * 授权 URL（获取 code）
     */
    private String publicAppAuthorize;

}
