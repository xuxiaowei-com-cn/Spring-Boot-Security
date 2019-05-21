package cn.com.xuxiaowei.security.service.impl;

import cn.com.xuxiaowei.security.entity.Qq;
import cn.com.xuxiaowei.security.mapper.QqMapper;
import cn.com.xuxiaowei.security.service.IQqService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    /**
     * 根据 openId，查询 QQ
     *
     * @param openId openId，openId 具有唯一性
     * @return 返回 根据 openId 查询的 QQ（仅有一条数据，多数据报错）
     */
    @Override
    public Qq getOpenId(String openId) {

        QueryWrapper<Qq> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(Qq.OPEN_ID, openId);

        return super.getOne(queryWrapper);
    }
}
