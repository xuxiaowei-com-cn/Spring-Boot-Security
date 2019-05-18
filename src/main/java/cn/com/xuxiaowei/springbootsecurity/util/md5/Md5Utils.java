package cn.com.xuxiaowei.springbootsecurity.util.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具类
 *
 * @author xuxiaowei
 */
public class Md5Utils {

    /**
     * MD5加密
     *
     * @param data 需要 md5 加密的字符串
     * @return md5 加密的字符串（32位，小写）
     */
    public static String getMD5(String data) {
        // 返回值
        String m = null;
        try {
            // 获得 将数据转换成 byte类型 的数组
            byte[] bytes = data.getBytes();
            // 指定算法的MessageDigest对象
            MessageDigest md5 = MessageDigest.getInstance("md5");
            // 将 byte类型 的数组 数据 转换成 byte类型 的数组的 md5 值
            byte[] digest = md5.digest(bytes);
            // 创建一个 StringBuilder，用于拼接 byte类型数组转成 String
            StringBuilder buffer = new StringBuilder();
            // 遍历
            for (byte b : digest) {
                // 与运算
                int i = b & 0xff;
                // byte 类型的数字，转换成 String
                String s = Integer.toHexString(i);
                // 一位数字，前面加0
                if (s.length() == 1) {
                    buffer.append("0");
                }
                // 拼接
                buffer.append(s);
            }
            m = buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return m;
    }
}
