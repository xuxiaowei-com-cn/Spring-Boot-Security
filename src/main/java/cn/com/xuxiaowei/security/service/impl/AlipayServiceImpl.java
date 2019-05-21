package cn.com.xuxiaowei.security.service.impl;

import cn.com.xuxiaowei.security.entity.Alipay;
import cn.com.xuxiaowei.security.mapper.AlipayMapper;
import cn.com.xuxiaowei.security.service.IAlipayService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付宝 服务实现类
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-21
 */
@Service
public class AlipayServiceImpl extends ServiceImpl<AlipayMapper, Alipay> implements IAlipayService {

    /**
     * 根据 userId（支付宝），查询 Alipay
     *
     * @param userId userId（支付宝），userId（支付宝） 具有唯一性
     * @return 返回 根据 userId（支付宝） 查询的 Alipay（仅有一条数据，多数据报错）
     */
    @Override
    public Alipay getUserId(String userId) {

        QueryWrapper<Alipay> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(Alipay.USER_ID, userId);

        return super.getOne(queryWrapper);
    }
}
