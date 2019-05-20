package cn.com.xuxiaowei.springbootsecurity.service;

import cn.com.xuxiaowei.springbootsecurity.entity.WeChat;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 微信 服务类
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-21
 */
public interface IWeChatService extends IService<WeChat> {

    /**
     * 根据 openId，查询 WeChat
     *
     * @param openId openId，openId 具有唯一性
     * @return 返回 根据 openId 查询的 WeChat（仅有一条数据，多数据报错）
     */
    WeChat getOpenId(String openId);

}
