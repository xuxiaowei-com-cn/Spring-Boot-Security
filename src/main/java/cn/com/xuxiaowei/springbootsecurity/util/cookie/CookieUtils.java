package cn.com.xuxiaowei.springbootsecurity.util.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie 工具类
 *
 * @author xuxiaowei
 */
public class CookieUtils {

    /**
     * 设置 Session Cookie 有效时间
     *
     * @param second Cookie 有效时间（秒）
     */
    public static void setSessionCookieTime(HttpServletRequest request, HttpServletResponse response,
                                            int second) {

        String jSessionId = "JSESSIONID";

        setCookieTime(request, response, jSessionId, "/", second);
    }

    /**
     * 设置 Cookie 有效时间
     *
     * @param cookieName Cookie 名称
     * @param url        Cookie 路径
     * @param second     Cookie 有效时间（秒）
     */
    public static void setCookieTime(HttpServletRequest request, HttpServletResponse response,
                                     String cookieName, String url, int second) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                String name = c.getName();
                if (cookieName.equals(name)) {
                    c.setMaxAge(second);
                    c.setPath(url);
                    response.addCookie(c);
                }
            }
        }
    }

}
