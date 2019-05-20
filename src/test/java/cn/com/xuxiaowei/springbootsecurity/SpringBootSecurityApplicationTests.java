package cn.com.xuxiaowei.springbootsecurity;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootSecurityApplicationTests {

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

}
