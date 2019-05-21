package com.qq.connect.oauth;

import com.qq.connect.QQConnect;
import com.qq.connect.QQConnectException;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.RandomStatusGenerator;
import com.qq.connect.utils.http.PostParameter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xuxiaowei
 */
public class Oauth extends QQConnect {
    private static final long serialVersionUID = -7860508274941797293L;

    private Pattern compile = Pattern.compile("code=(\\w+)&state=(\\w+)&?");

    public Oauth() {
    }

    private String[] extractionAuthCodeFromUrl(String url) throws QQConnectException {
        if (url == null) {
            throw new QQConnectException("you pass a null String object");
        } else {

            Matcher m = compile.matcher(url);

            String authCode = "";
            String state = "";
            if (m.find()) {
                authCode = m.group(1);
                state = m.group(2);
            }

            return new String[]{authCode, state};
        }
    }

    public AccessToken getAccessTokenByRequest(ServletRequest request) throws QQConnectException {
        String queryString = ((HttpServletRequest) request).getQueryString();
        if (queryString == null) {
            return new AccessToken();
        } else {
            String state = (String) ((HttpServletRequest) request).getSession().getAttribute("qq_connect_state");
            if (state != null && !state.equals("")) {
                String[] authCodeAndState = this.extractionAuthCodeFromUrl(queryString);
                String returnState = authCodeAndState[1];
                String returnAuthCode = authCodeAndState[0];
                AccessToken accessTokenObj = null;
                if (!returnState.equals("") && !returnAuthCode.equals("")) {
                    if (!state.equals(returnState)) {
                        accessTokenObj = new AccessToken();
                    } else {
                        accessTokenObj = new AccessToken(this.client.post(QQConnectConfig.getValue("accessTokenURL"), new PostParameter[]{new PostParameter("client_id", QQConnectConfig.getValue("app_ID")), new PostParameter("client_secret", QQConnectConfig.getValue("app_KEY")), new PostParameter("grant_type", "authorization_code"), new PostParameter("code", returnAuthCode), new PostParameter("redirect_uri", QQConnectConfig.getValue("redirect_URI"))}, false));
                    }
                } else {
                    accessTokenObj = new AccessToken();
                }

                return accessTokenObj;
            } else {
                return new AccessToken();
            }
        }
    }

    /**
     * @deprecated
     */
    public AccessToken getAccessTokenByQueryString(String queryString, String state) throws QQConnectException {
        if (queryString == null) {
            return new AccessToken();
        } else {
            String[] authCodeAndState = this.extractionAuthCodeFromUrl(queryString);
            String returnState = authCodeAndState[1];
            String returnAuthCode = authCodeAndState[0];
            AccessToken accessTokenObj = null;
            if (!returnState.equals("") && !returnAuthCode.equals("")) {
                if (!state.equals(returnState)) {
                    accessTokenObj = new AccessToken();
                } else {
                    accessTokenObj = new AccessToken(this.client.post(QQConnectConfig.getValue("accessTokenURL"), new PostParameter[]{new PostParameter("client_id", QQConnectConfig.getValue("app_ID")), new PostParameter("client_secret", QQConnectConfig.getValue("app_KEY")), new PostParameter("grant_type", "authorization_code"), new PostParameter("code", returnAuthCode), new PostParameter("redirect_uri", QQConnectConfig.getValue("redirect_URI"))}, false));
                }
            } else {
                accessTokenObj = new AccessToken();
            }

            return accessTokenObj;
        }
    }

    /**
     * @deprecated
     */
    public String getAuthorizeURL(String scope, String state) throws QQConnectException {
        return QQConnectConfig.getValue("authorizeURL").trim() + "?client_id=" + QQConnectConfig.getValue("app_ID").trim() + "&redirect_uri=" + QQConnectConfig.getValue("redirect_URI").trim() + "&response_type=" + "code" + "&state=" + state + "&scope=" + scope;
    }

    /**
     * @deprecated
     */
    public String getAuthorizeURL(String state) throws QQConnectException {
        String scope = QQConnectConfig.getValue("scope");
        return scope != null && !scope.equals("") ? this.getAuthorizeURL("code", state, scope) : QQConnectConfig.getValue("authorizeURL").trim() + "?client_id=" + QQConnectConfig.getValue("app_ID").trim() + "&redirect_uri=" + QQConnectConfig.getValue("redirect_URI").trim() + "&response_type=" + "code" + "&state=" + state;
    }

    /**
     * @deprecated
     */
    public String getAuthorizeURLByScope(String scope, ServletRequest request) throws QQConnectException {
        String state = RandomStatusGenerator.getUniqueState();
        ((HttpServletRequest) request).setAttribute("qq_connect_state", state);
        return QQConnectConfig.getValue("authorizeURL").trim() + "?client_id=" + QQConnectConfig.getValue("app_ID").trim() + "&redirect_uri=" + QQConnectConfig.getValue("redirect_URI").trim() + "&response_type=" + "code" + "&state=" + state + "&scope=" + scope;
    }

    public String getAuthorizeURL(ServletRequest request) throws QQConnectException {
        String state = RandomStatusGenerator.getUniqueState();
        ((HttpServletRequest) request).getSession().setAttribute("qq_connect_state", state);
        String scope = QQConnectConfig.getValue("scope");
        return scope != null && !scope.equals("") ? this.getAuthorizeURL("code", state, scope) : QQConnectConfig.getValue("authorizeURL").trim() + "?client_id=" + QQConnectConfig.getValue("app_ID").trim() + "&redirect_uri=" + QQConnectConfig.getValue("redirect_URI").trim() + "&response_type=" + "code" + "&state=" + state;
    }

    /**
     * @deprecated
     */
    public String getAuthorizeURL(String response_type, String state, String scope) throws QQConnectException {
        return QQConnectConfig.getValue("authorizeURL").trim() + "?client_id=" + QQConnectConfig.getValue("app_ID").trim() + "&redirect_uri=" + QQConnectConfig.getValue("redirect_URI").trim() + "&response_type=" + response_type + "&state=" + state + "&scope=" + scope;
    }
}
