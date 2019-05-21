package cn.com.xuxiaowei.security.service;

import cn.com.xuxiaowei.security.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表（测试） 服务类
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-18
 */
public interface IUserService extends IService<User> {

    /**
     * 根据手机号，查询用户
     *
     * @param phone 手机号，手机号具有唯一性
     * @return 返回 根据手机号查询的用户（仅有一条数据，多数据报错）
     */
    User getPhone(String phone);

}
