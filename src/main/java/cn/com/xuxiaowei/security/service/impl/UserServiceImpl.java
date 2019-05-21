package cn.com.xuxiaowei.security.service.impl;

import cn.com.xuxiaowei.security.entity.User;
import cn.com.xuxiaowei.security.mapper.UserMapper;
import cn.com.xuxiaowei.security.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表（测试） 服务实现类
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 根据手机号，查询用户
     *
     * @param phone 手机号，手机号具有唯一性
     * @return 返回 根据手机号查询的用户（仅有一条数据，多数据报错）
     */
    @Override
    public User getPhone(String phone) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(User.PHONE, phone);

        return super.getOne(queryWrapper);
    }

}
