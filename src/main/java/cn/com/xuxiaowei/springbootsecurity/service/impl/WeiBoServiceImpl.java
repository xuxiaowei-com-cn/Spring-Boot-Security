package cn.com.xuxiaowei.springbootsecurity.service.impl;

import cn.com.xuxiaowei.springbootsecurity.entity.WeiBo;
import cn.com.xuxiaowei.springbootsecurity.mapper.WeiBoMapper;
import cn.com.xuxiaowei.springbootsecurity.service.IWeiBoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微博 服务实现类
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-21
 */
@Service
public class WeiBoServiceImpl extends ServiceImpl<WeiBoMapper, WeiBo> implements IWeiBoService {

}
