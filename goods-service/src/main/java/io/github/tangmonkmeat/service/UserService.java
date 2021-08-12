package io.github.tangmonkmeat.service;

import io.github.tangmonkmeat.model.Goods;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Description:
 *
 * @author zwl
 * @date 2021/7/23 下午8:22
 * @version 1.0
 */
public interface UserService {

    /**
     * 缓存 用户的收藏或者取消收藏
     *
     * @param goodsId 商品ID
     * @param openId 用户openId
     * @param hasCollect true收藏，否则取消
     */
    void cacheCollectAddOrDelete(long goodsId, String openId, boolean hasCollect);

    /**
     *  用户的收藏或者取消收藏 入库
     *
     * @param goodsId 商品ID
     * @param openId 用户openId
     * @param hasCollect true收藏，否则取消
     */
    void collectAddOrDelete(long goodsId, String openId, boolean hasCollect);

    /**
     * 用户收藏入库
     *
     * @param openId
     * @param goodsIdList
     */
    void collectAdd(String openId, List<Long> goodsIdList);

    /**
     * 用户收藏取消
     *
     * @param openId
     * @param goodsList
     */
    void collectDelete(String openId,List<Long> goodsList);

    /**
     * 用户是否收藏
     *
     * @param openId
     * @param goodsId
     */
    boolean userHasCollect(String openId,long goodsId);

    /**
     * 获取用户收藏列表
     *
     * @param openId
     * @param page
     * @param size
     * @return
     */
    List<Goods> getUserCollectList(String openId, int page, int size);

    /**
     * 获取用户买过的商品
     *
     * @param openId
     * @param page
     * @param size
     * @return
     */
    List<Goods> getUserBought(String openId, int page, int size);

    /**
     * 获取用户卖出的商品
     *
     * @param openId
     * @param page
     * @param size
     * @return
     */
    List<Goods> getUserSold(String openId, int page, int size);

    /**
     * 获取用户发布的，还没卖出的商品
     *
     * @param openId
     * @param page
     * @param size
     * @return
     */
    List<Goods> getUserPosted(String openId, int page, int size);

    /**
     * 用户历史
     *
     * @param userId
     * @param page
     * @param size
     * @return key=日期 , value=对哪些商品进行了操作
     */
    LinkedHashMap<String, List<Goods>> getUserHistoryList(String userId, int page, int size);

    /**
     * 添加
     *
     * @param goodsId
     * @param sellerId
     */
    void addWant(int goodsId, String sellerId);
}
