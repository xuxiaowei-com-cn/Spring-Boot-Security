package cn.com.xuxiaowei.springbootsecurity.service.impl;

import cn.com.xuxiaowei.springbootsecurity.entity.User;
import cn.com.xuxiaowei.springbootsecurity.mapper.UserMapper;
import cn.com.xuxiaowei.springbootsecurity.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表（测试） 服务实现类
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 根据用户名，查询用户
     *
     * @param username 用户名
     * @return 返回 根据用户名查询的用户
     */
    @Override
    public User getUsername(String username) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(User.USERNAME,username);

        return super.getOne(queryWrapper);
    }

}
