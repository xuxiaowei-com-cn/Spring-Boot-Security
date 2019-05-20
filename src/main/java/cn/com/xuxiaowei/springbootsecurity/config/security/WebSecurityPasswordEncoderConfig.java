package cn.com.xuxiaowei.springbootsecurity.config.security;

import cn.com.xuxiaowei.springbootsecurity.util.md5.Md5Utils;
import cn.com.xuxiaowei.springbootsecurity.util.rsa.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;

/**
 * 密码编码器
 *
 * @author xuxiaowei
 */
@Slf4j
public class WebSecurityPasswordEncoderConfig implements PasswordEncoder {

    @Autowired
    private HttpServletRequest request;

    /**
     * 编码原始密码。
     * 通常，良好的编码算法应用SHA-1或更大的散列以及8字节或更大的随机生成的盐。
     *
     * @param rawPassword 原始密码，内容为：userNotFoundPassword
     * @return 经过算法后的数据
     */
    @Override
    public String encode(CharSequence rawPassword) {

        return rawPassword.toString();
    }

    /**
     * 验证从存储中获取的编码密码是否与编码后提交的原始密码相匹配。
     * 如果密码匹配则返回true，否则返回false。
     * 存储的密码本身永远不会被解码。
     *
     * @param rawPassword     用于编码和匹配的原始密码
     * @param encodedPassword 来自存储的编码密码与之比较
     * @return 如果编码后的原始密码与存储中的编码密码匹配，则为true
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        HttpSession session = request.getSession();

        String rsaPrivateKey = (String) session.getAttribute("RsaPrivateKey");

        String password = rawPassword.toString();

        boolean equals;

        try {

            password = new String(RsaUtils.decryptByPrivateKey(password, rsaPrivateKey));

            password = Md5Utils.getMD5(password);

            equals = password.equals(encodedPassword);

        } catch (GeneralSecurityException e) {

            log.debug("使用 RSA 解码密码失败！");

            // <code>e.printStackTrace();</code>

            return false;
        }

        return equals;
    }
}
