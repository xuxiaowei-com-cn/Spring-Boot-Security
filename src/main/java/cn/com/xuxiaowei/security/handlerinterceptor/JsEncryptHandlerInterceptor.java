package cn.com.xuxiaowei.security.handlerinterceptor;

import cn.com.xuxiaowei.security.util.rsa.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * JS 非对称性加密 拦截器
 *
 * @author xuxiaowei
 */
@Slf4j
public class JsEncryptHandlerInterceptor implements HandlerInterceptor {

    /**
     * 公钥
     */
    private static String publicKey;

    /**
     * 私钥
     */
    private static String privateKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        generateRsa();

        // 私钥放入 session
        request.getSession().setAttribute("RsaPrivateKey", privateKey);
        // 公钥返回页面
        modelAndView.addObject("RsaPublicKey", publicKey);

    }

    /**
     * 生成 RSA
     */
    private void generateRsa() {

        try {

            // 初始化密钥
            Map<String, Key> stringKeyMap = RsaUtils.initKey();

            // 获取公钥和私钥
            publicKey = RsaUtils.getPublicKey(stringKeyMap);
            privateKey = RsaUtils.getPrivateKey(stringKeyMap);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            log.debug("初始化密钥失败！");

        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
