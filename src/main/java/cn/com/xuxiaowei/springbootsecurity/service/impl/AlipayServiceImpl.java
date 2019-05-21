package cn.com.xuxiaowei.springbootsecurity.service.impl;

import cn.com.xuxiaowei.springbootsecurity.entity.Alipay;
import cn.com.xuxiaowei.springbootsecurity.mapper.AlipayMapper;
import cn.com.xuxiaowei.springbootsecurity.service.IAlipayService;
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

}
