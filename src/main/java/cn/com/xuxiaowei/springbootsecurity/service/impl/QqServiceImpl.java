package cn.com.xuxiaowei.springbootsecurity.service.impl;

import cn.com.xuxiaowei.springbootsecurity.entity.Qq;
import cn.com.xuxiaowei.springbootsecurity.mapper.QqMapper;
import cn.com.xuxiaowei.springbootsecurity.service.IQqService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * QQ 服务实现类
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-20
 */
@Service
public class QqServiceImpl extends ServiceImpl<QqMapper, Qq> implements IQqService {

}
