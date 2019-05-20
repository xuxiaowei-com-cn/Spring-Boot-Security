package cn.com.xuxiaowei.springbootsecurity.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;

/**
 * Security Utils
 *
 * @author xuxiaowei
 */
public class SecurityUtils {

    /**
     * 定义与当前执行线程关联的最小安全信息的接口。
     * <p>
     * 安全信息文存储在{@link SecurityContextHolder}中。
     */
    public static SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }

    /**
     * 认证信息
     */
    public static Authentication getAuthentication() {
        return getContext().getAuthentication();
    }

    //////////////////////////////////////////////////////////////////////

    /**
     * 名字
     */
    public static String getName() {
        return getAuthentication().getName();
    }

    //////////////////////////////////////////////////////////////////////

    /**
     * 获取用户权限
     */
    public static Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthentication().getAuthorities();
    }

    /**
     * 获取用户权限
     */
    public static GrantedAuthority[] getGrantedAuthoritys() {
        int size = getAuthorities().size();
        return getAuthorities().toArray(new GrantedAuthority[size]);
    }

    /**
     * 获取用户权限
     */
    public static String[] getGrantedAuthoritysString() {
        GrantedAuthority[] grantedAuthoritys = getGrantedAuthoritys();
        int length = grantedAuthoritys.length;
        String[] strings = new String[length];
        for (int i = 0; i < length; i++) {
            strings[i] = grantedAuthoritys[i].toString();
        }
        return strings;
    }

    //////////////////////////////////////////////////////////////////////

    /**
     * 自定义信息、如：WebAuthenticationDetails
     */
    public static Object getCredentialsObj() {
        return getAuthentication().getCredentials();
    }

    /**
     * 自定义信息、如：WebAuthenticationDetails
     */
    public static WebAuthenticationDetails getCredentials() {
        Object credentialsObj = getCredentialsObj();
        if (credentialsObj instanceof WebAuthenticationDetails) {
            return (WebAuthenticationDetails) credentialsObj;
        } else {
            return null;
        }
    }

    //////////////////////////////////////////////////////////////////////

    /**
     * 与Web身份验证请求相关的所选HTTP详细信息的持有者（Object 对象）。
     * <p>
     * 使用用户名与密码登录时，有值，否则为空
     */
    public static Object getDetailsObj() {
        return getAuthentication().getDetails();
    }

    /**
     * 记录远程地址，如果会话已存在（也不会创建会话），还会设置会话ID。
     * <p>
     * 使用用户名与密码登录时，有值，否则为空
     * <p>
     * 1、remoteAddress
     * 2、sessionId
     */
    public static WebAuthenticationDetails getDetails() {
        Object detailsObj = getDetailsObj();
        if (detailsObj instanceof WebAuthenticationDetails) {
            return (WebAuthenticationDetails) detailsObj;
        } else {
            return null;
        }
    }

    //////////////////////////////////////////////////////////////////////

    /**
     * 获取用户 IP
     * <p>
     * 使用 用户名和密码 登录时，直接可获取 IP
     * 第三方登录时，需要在授权之前先获取 details，在授权时放入 credentials 中
     */
    public static String getRemoteAddress() {
        WebAuthenticationDetails details = getDetails();
        WebAuthenticationDetails credentials = getCredentials();
        if (details == null) {
            if (credentials == null) {
                return null;
            } else {
                return credentials.getRemoteAddress();
            }
        } else {
            return details.getRemoteAddress();
        }
    }

    //////////////////////////////////////////////////////////////////////

    /**
     * 用户信息
     */
    public static Object getPrincipalObj() {
        return getAuthentication().getPrincipal();
    }

    /**
     * 用户信息
     * <p>
     * 注意此时的类型
     * <p>
     * 第三方登录（包括短信登录）时，注意放置的类型
     */
    public static org.springframework.security.core.userdetails.User getPrincipal() {
        Object principalObj = getPrincipalObj();
        if (principalObj instanceof org.springframework.security.core.userdetails.User) {
            return (org.springframework.security.core.userdetails.User) principalObj;
        } else {
            return null;
        }
    }

    //////////////////////////////////////////////////////////////////////

    /**
     * 未登录 时，principalObj 为 anonymousUser，String 类型
     */
    public static boolean isLogin() {
        Object principalObj = getPrincipalObj();
        return !(principalObj instanceof String);
    }

}
