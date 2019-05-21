package cn.com.xuxiaowei.springbootsecurity.service;

import cn.com.xuxiaowei.springbootsecurity.entity.WeiBo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 微博 服务类
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-21
 */
public interface IWeiBoService extends IService<WeiBo> {

    /**
     * 根据 id（微博），查询 微博
     *
     * @param id id（微博），id（微博） 具有唯一性
     * @return 返回 根据 id（微博） 查询的 微博（仅有一条数据，多数据报错）
     */
    WeiBo getId(String id);

}
