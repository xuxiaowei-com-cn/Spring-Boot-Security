package cn.com.xuxiaowei.springbootsecurity;

import cn.com.xuxiaowei.springbootsecurity.setting.AlipaySettings;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootSecurityApplicationTests {

    @Autowired
    private AlipaySettings alipaySettings;

    @Test
    public void contextLoads() {
    }

    /**
     * 根据 AccessToken（未过期）和 OpenID 获取 QQ 信息
     * <p>
     * 还使用了 QQ 配置文件中的 app_ID，在 com.qq.connect.api.qzone.UserInfo 中自动获取了
     */
    @Test
    public void getQQInfoByAccessTokenAndOpenID() {

        // 获取 用户 userInfo（com.qq.connect.api.qzone.UserInfo）
        // 使用的 配置文件名：getUserInfoURL
        // 使用的 接口URL：https://graph.qq.com/user/get_user_info
        // 官方文档：http://wiki.connect.qq.com/openapi%E8%B0%83%E7%94%A8%E8%AF%B4%E6%98%8E_oauth2-0
        // OpenAPI调用说明_OAuth2.0
        UserInfo userInfo = new UserInfo("QQ 登录时保存的 AccessToken", "QQ 登录时的 OpenID");

        try {

            // 获取 用户 userInfoBean（com.qq.connect.javabeans.qzone.UserInfoBean） QQ 详细信息
            UserInfoBean userInfoBean = userInfo.getUserInfo();

            log.debug(userInfoBean.toString());

        } catch (QQConnectException e) {
            e.printStackTrace();
        }

    }

    /**
     * 刷新 QQ AccessToken
     */
    @Test
    public void QQRefreshAccessToken() throws QQConnectException {

        Oauth oauth = new Oauth();

        // 在 Step2（http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token） 中，返回的refres_token。
        AccessToken accessToken = oauth.refreshAccessToken("QQ 登录时的 refres_token");

        System.err.println(accessToken);

    }

    /**
     * 根据 AccessToken 获取 支付宝 信息
     */
    @Test
    public void getAlipayInfoByAuthToken() throws AlipayApiException {

        String appId = alipaySettings.getAppId();
        String appPrivateKey = alipaySettings.getAppPrivateKey().replace(" ", "");
        String alipayPublicKey = alipaySettings.getAlipayPublicKey();
        String charset = alipaySettings.getCharset();
        String url = alipaySettings.getUrl();
        String format = alipaySettings.getFormat();
        String signType = alipaySettings.getSignType();


        // 蚂蚁金服 授权基础类
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, appPrivateKey, format, charset, alipayPublicKey, signType);

        // 蚂蚁金服 用户信息 分享 请求 Object
        AlipayUserInfoShareRequest alipayUserInfoShareRequest = new AlipayUserInfoShareRequest();

        // 蚂蚁金服 用户信息 分享 响应 Object
        AlipayUserInfoShareResponse alipayUserInfoShareResponse = alipayClient.execute(alipayUserInfoShareRequest, "支付宝 登录时保存的 AccessToken");

        // 转化为 StringBuilder，AlipayUserInfoShareResponse 中未重写 toString()
        String alipayUserInfoShareResponseStringBuilder = ReflectionToStringBuilder.toString(alipayUserInfoShareResponse);

        log.debug(alipayUserInfoShareResponseStringBuilder);

    }

}
