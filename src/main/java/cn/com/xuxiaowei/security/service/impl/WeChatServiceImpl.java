package cn.com.xuxiaowei.security.service.impl;

import cn.com.xuxiaowei.security.entity.WeChat;
import cn.com.xuxiaowei.security.mapper.WeChatMapper;
import cn.com.xuxiaowei.security.service.IWeChatService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    /**
     * 根据 openId，查询 WeChat
     *
     * @param openId openId，openId 具有唯一性
     * @return 返回 根据 openId 查询的 WeChat（仅有一条数据，多数据报错）
     */
    @Override
    public WeChat getOpenId(String openId) {

        QueryWrapper<WeChat> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(WeChat.OPENID, openId);

        return super.getOne(queryWrapper);
    }

}
