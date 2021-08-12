package io.github.tangmonkmeat.service;

import io.github.tangmonkmeat.model.po.PostExample;
import io.github.tangmonkmeat.model.Category;
import io.github.tangmonkmeat.model.Region;

import java.util.List;

/**
 * Description: 商品发布业务操作
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 下午4:31
 */
public interface GoodsPublishService {

    /**
     * 保存发布的商品信息
     *
     * @param post PostImages
     */
    long postGoods(PostExample post);

    /**
     * 删除用户发布的商品
     *
     * @param goodsId 商品id
     * @param userId 用户id
     * @throws Exception {@link io.github.tangmonkmeat.exception.BusinessException}
     */
    int deleteGoods(int goodsId, String userId) throws Exception;

    /**
     * 获取区域列表
     *
     * @param regionId 区域id
     * @return 区域列表
     */
    List<Region> getRegionList(int regionId);

    /**
     * 获取指定分类id的分类列表
     *
     * @param cateId 分类id
     * @return 分类列表
     */
    List<Category> getCateList(int cateId);
}
