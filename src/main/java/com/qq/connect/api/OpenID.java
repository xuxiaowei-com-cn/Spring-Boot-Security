package com.qq.connect.api;

import com.qq.connect.QQConnect;
import com.qq.connect.QQConnectException;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.http.PostParameter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuxiaowei
 */
public class OpenID extends QQConnect {
    private static final long serialVersionUID = 6913005509508673584L;

    private Pattern compile = Pattern.compile("\"openid\"\\s*:\\s*\"(\\w+)\"");

    /**
     * 是否获取 UnionId
     */
    private boolean getUnionId;

    public OpenID(String token) {
        this.client.setToken(token);
    }

    /**
     * 带有控制是否获取 UnionId 的构造器
     */
    public OpenID(String token, String openID, boolean getUnionId) {
        super(token, openID);
        this.getUnionId = getUnionId;
    }

    private String getUserOpenID(String accessToken) throws QQConnectException {
        String openid = "";

        PostParameter[] accessTokens = {new PostParameter("access_token", accessToken)};

        String jsonp = this.client.get(QQConnectConfig.getValue("getOpenIDURL"), accessTokens).asString();

        Matcher m = compile.matcher(jsonp);
        if (m.find()) {
            openid = m.group(1);
            return openid;
        } else {
            throw new QQConnectException("server error!");
        }
    }

    public String getUserOpenID() throws QQConnectException {
        String accessToken = this.client.getToken();
        return this.getUserOpenID(accessToken);
    }
}
