package cn.com.xuxiaowei.springbootsecurity.service.impl;

import cn.com.xuxiaowei.springbootsecurity.entity.Qq;
import cn.com.xuxiaowei.springbootsecurity.entity.WeiBo;
import cn.com.xuxiaowei.springbootsecurity.mapper.WeiBoMapper;
import cn.com.xuxiaowei.springbootsecurity.service.IWeiBoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微博 服务实现类
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-21
 */
@Service
public class WeiBoServiceImpl extends ServiceImpl<WeiBoMapper, WeiBo> implements IWeiBoService {

    /**
     * 根据 id（微博），查询 微博
     *
     * @param id id（微博），id（微博） 具有唯一性
     * @return 返回 根据 id（微博） 查询的 微博（仅有一条数据，多数据报错）
     */
    @Override
    public WeiBo getId(String id) {
        QueryWrapper<WeiBo> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(WeiBo.ID, id);

        return super.getOne(queryWrapper);
    }
}
