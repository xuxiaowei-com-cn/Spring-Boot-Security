package cn.com.xuxiaowei.security.service;

import cn.com.xuxiaowei.security.entity.Alipay;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 支付宝 服务类
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-21
 */
public interface IAlipayService extends IService<Alipay> {

    /**
     * 根据 userId（支付宝），查询 Alipay
     *
     * @param userId userId（支付宝），userId（支付宝） 具有唯一性
     * @return 返回 根据 userId（支付宝） 查询的 Alipay（仅有一条数据，多数据报错）
     */
    Alipay getUserId(String userId);

}
