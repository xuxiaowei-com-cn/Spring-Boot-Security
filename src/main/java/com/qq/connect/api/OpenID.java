package com.qq.connect.api;

import com.qq.connect.QQConnect;
import com.qq.connect.QQConnectException;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.http.PostParameter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 重写后，新增获取 unionId 的方法
 * <p>
 * 前提是成功申请 [开发者反馈](http://wiki.connect.qq.com/%E5%BC%80%E5%8F%91%E8%80%85%E5%8F%8D%E9%A6%88) 中的 应用打通
 *
 * @author xuxiaowei
 */
public class OpenID extends QQConnect {
    private static final long serialVersionUID = 6913005509508673584L;

    /**
     * 正则表达式 增加 unionid
     */
    private Pattern compile = Pattern.compile("\"openid\"\\s*:\\s*\"(\\w+)\",\"unionid\"\\s*:\\s*\"(\\w+)\"");

    /**
     * 是否获取 UnionId
     */
    private boolean getUnionId;

    /**
     * UnionId
     */
    private String unionId;

    /**
     * 带有控制是否获取 UnionId 的构造器
     *
     * @param token      AccessToken
     * @param getUnionId 为 true，获取 UnionId（需要申请 [开发者反馈](http://wiki.connect.qq.com/%E5%BC%80%E5%8F%91%E8%80%85%E5%8F%8D%E9%A6%88) 中的 应用打通）
     *                   为 false，不获取 UnionId
     */
    public OpenID(String token, boolean getUnionId) {
        this.client.setToken(token);
        this.getUnionId = getUnionId;
    }

    private String getUserOpenID(String accessToken) throws QQConnectException {
        String openid = "";

        PostParameter[] accessTokens = {new PostParameter("access_token", accessToken)};

        if (getUnionId) {
            accessTokens = new PostParameter[]{new PostParameter("access_token", accessToken), new PostParameter("unionid", 1)};
        }

        String jsonp = this.client.get(QQConnectConfig.getValue("getOpenIDURL"), accessTokens).asString();

        Matcher m = compile.matcher(jsonp);
        if (m.find()) {
            openid = m.group(1);

            if (getUnionId) {
                unionId = m.group(2);
            }

            return openid;
        } else {
            throw new QQConnectException("server error!");
        }
    }

    public String getUserOpenID() throws QQConnectException {
        String accessToken = this.client.getToken();
        return this.getUserOpenID(accessToken);
    }

    /**
     * 该方法调用需要满足以下条件：
     * <p>
     * 1、申请 应用打通 成功后，操作说明参见：[开发者反馈](http://wiki.connect.qq.com/%E5%BC%80%E5%8F%91%E8%80%85%E5%8F%8D%E9%A6%88) 中的 应用打通
     * 2、getUnionId = true
     * 3、在调用 getUserOpenID(String accessToken) 之后再调用
     */
    public String getUnionId() {
        return unionId;
    }

}
