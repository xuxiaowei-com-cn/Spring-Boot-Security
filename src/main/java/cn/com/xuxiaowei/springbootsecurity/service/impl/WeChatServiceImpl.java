package cn.com.xuxiaowei.springbootsecurity.service.impl;

import cn.com.xuxiaowei.springbootsecurity.entity.WeChat;
import cn.com.xuxiaowei.springbootsecurity.mapper.WeChatMapper;
import cn.com.xuxiaowei.springbootsecurity.service.IWeChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信 服务实现类
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-21
 */
@Service
public class WeChatServiceImpl extends ServiceImpl<WeChatMapper, WeChat> implements IWeChatService {

}
