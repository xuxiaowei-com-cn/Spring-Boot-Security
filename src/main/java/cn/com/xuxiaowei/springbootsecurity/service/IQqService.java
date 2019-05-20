package cn.com.xuxiaowei.springbootsecurity.service;

import cn.com.xuxiaowei.springbootsecurity.entity.Qq;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * QQ 服务类
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-20
 */
public interface IQqService extends IService<Qq> {

    /**
     * 根据 openId，查询 QQ
     *
     * @param openId openId，openId 具有唯一性
     * @return 返回 根据 openId 查询的 QQ（仅有一条数据，多数据报错）
     */
    Qq getOpenId(String openId);

}
