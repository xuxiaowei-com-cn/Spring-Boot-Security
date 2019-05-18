package cn.com.xuxiaowei.springbootsecurity.service;

import cn.com.xuxiaowei.springbootsecurity.entity.User;
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
     * 根据用户名，查询用户
     *
     * @param username 用户名，用户名具有唯一性
     * @return 返回 根据用户名查询的用户（仅有一条数据，多数据报错）
     */
    User getUsername(String username);

}
