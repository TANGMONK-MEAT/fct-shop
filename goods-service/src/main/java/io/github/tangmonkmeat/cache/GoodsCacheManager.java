package io.github.tangmonkmeat.cache;

import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import io.github.tangmonkmeat.constant.GoodsConstant;
import io.github.tangmonkmeat.model.*;
import io.github.tangmonkmeat.service.GoodsService;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 下午7:35
 */
@Component
public class GoodsCacheManager {

    private final Logger LOGGER = LoggerFactory.getLogger(GoodsCacheManager.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisScript<Long> cacheGoodsMapScript;

    @Autowired
    private RedisScript<Long> cacheGoodsListScript;

    private final String VIEW_PRODUCT_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.VIEW_PRODUCT_KEY, "goodsInfoMap");

    private final String LEVEL_REGION_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.LEVEL_REGION_KEY, "regionInfoMap");

    private final String MAIN_CATEGORY_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.MAIN_CATEGORY_KEY, "field");

    private final String SUB_CATEGORY_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.SUB_CATEGORY_KEY, "field");

    private final String INDEX_GOODS_LIST_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.INDEX_GOODS_LIST_KEY, "index");

    private final String INDEX_AD_LIST_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.INDEX_AD_LIST_KEY, "index");

    private final String INDEX_BANNER_LIST_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.INDEX_BANNER_LIST_KEY, "index");

    private final String CATEGORY_GOODS_PAGE_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.CATEGORY_GOODS_PAGE_KEY,"pageNum");

    /**
     * 缓存商品信息
     *
     * @param goods 商品信息
     */
    public void cacheGoodsInfoAsMap(Goods goods) {
        final Long id = goods.getId();
        if (ObjectUtils.isEmpty(id)) {
            return;
        }
        List<String> keys = new ArrayList<>(1);
        keys.add(VIEW_PRODUCT_KEY);
        redisService.eval(cacheGoodsMapScript, keys, id + "", "", GoodsConstant.INDEX_GOODS_LIST_EXPIRE + "");
        redisService.hset(VIEW_PRODUCT_KEY,id + "",goods);
        LOGGER.info("缓存商品信息，goodsInfo：{}", goods);
    }

    public void cacheGoodsInfoAsMap(Long goodsId) {
        if (ObjectUtils.isEmpty(goodsId)) {
            return;
        }
        Goods goods = goodsService.getGoodsByGoodsId(goodsId);
        List<String> keys = new ArrayList<>(1);
        keys.add(VIEW_PRODUCT_KEY);
        final String hashKey = goodsId + "";
        redisService.eval(cacheGoodsMapScript, keys, hashKey, "", GoodsConstant.VIEW_PRODUCT_KEY_EXPIRE + "");
        redisService.hset(VIEW_PRODUCT_KEY,hashKey,goods);
        LOGGER.info("缓存商品信息，goodsInfo：{}", goods);
    }

    public Goods getGoodsDetail(Long goodsId){
        return (Goods)redisService.hget(VIEW_PRODUCT_KEY,goodsId.toString());
    }

    public void cacheGoodsInfoMap(List<Goods> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        cacheGoodsInfoAsMap(list.get(0));
        list.remove(0);
        redisService.hmset(VIEW_PRODUCT_KEY, listToMap3(list));
        LOGGER.info("缓存商品信息，goodsInfo：{}", list);
    }

    public void cacheGoodsInfoList(List<Goods> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        // 设置过期时间，前提不存在
        List<String> keys = new ArrayList<>(1);
        keys.add(INDEX_GOODS_LIST_KEY);
        redisService.eval(cacheGoodsListScript, keys,"", GoodsConstant.INDEX_GOODS_LIST_EXPIRE + "");

        //
        List<Object> objects = new ArrayList<>(list.size());
        objects.addAll(list);
        redisService.rmpush(INDEX_GOODS_LIST_KEY, objects);
        LOGGER.info("缓存商品信息，goodsInfo：{}", list);
    }

    public List<Goods> getGoodsInfoListRange(int start, int end) {
        Object obj = redisService.lrange(INDEX_GOODS_LIST_KEY, start, end);
        if (obj != null){
            List<Goods> res = (List<Goods>)obj;
            if (res.size() == (end - start + 1)){
                return res;
            }
        }
        return null;
    }

    public void removeGoodsInfo(Long goodsId) {
        if (ObjectUtils.isEmpty(goodsId)) {
            return;
        }
        redisService.hdel(VIEW_PRODUCT_KEY, goodsId);
        LOGGER.info("移除缓存的商品信息，goodsId：{}", goodsId);
    }

    public void cacheRegionList(int parentId, List<Region> regionList) {
        final String key = LEVEL_REGION_KEY + ":" + parentId;
        redisService.hmset(key, listToMap(regionList));
        LOGGER.info("缓存的同一级别的地域信息，parentId: {}, regionList：{}", parentId, regionList);
    }

    public Collection<Object> getRegionList(int parentId) {
        final String key = LEVEL_REGION_KEY + ":" + parentId;
        Map<String,Object> map = (Map<String,Object>)redisService.hmget(key);
        return map.values();
    }

    public void cacheCategoryList(int parentId, List<Category> categoryList) {
        final String key = MAIN_CATEGORY_KEY + ":" + parentId;
        redisService.hmset(key, listToMap2(categoryList));
        LOGGER.info("缓存的最高级别0的分类信息，parentId: {}, categoryList：{}", parentId, categoryList);
    }

    public void cacheIndexAdList(List<Ad> adList) {
        List<Object> objects = new ArrayList<>(adList.size());
        objects.addAll(adList);
        redisService.rmpush(INDEX_AD_LIST_KEY, objects);
        LOGGER.info("缓存首页的 Ad列表信息，adList: {}", adList);
    }

    public List<Ad> getIndexAdList() {
        List<Ad> res = (List<Ad>)redisService.lrange(INDEX_AD_LIST_KEY, 0, -1);
        return res;
    }

    public void cacheIndexChannelList(List<Channel> channelList) {
        List<Object> objects = new ArrayList<>(channelList.size());
        objects.addAll(channelList);
        redisService.rmpush(INDEX_BANNER_LIST_KEY, objects);
        LOGGER.info("缓存首页的 channel列表信息，bannerList: {}", channelList);
    }

    public List<Channel> getIndexChannelList() {
        List<Channel> res = (List<Channel>)redisService.lrange(INDEX_BANNER_LIST_KEY, 0, -1);
        return res;
    }

    public Collection<Category> getCategoryList(int parentId) {
        final String key = MAIN_CATEGORY_KEY + ":" + parentId;
        Map<String, Category> map = (Map<String, Category>)redisService.hmget(key);
        return map.values();
    }

    public void cacheSubCategoryList(int id, List<Category> subList) {
        final String key = SUB_CATEGORY_KEY + ":" + id;
        redisService.hmset(key, listToMap2(subList));
        LOGGER.info("缓存的子分类的分类信息，parentId: {}, categoryList：{}", id, subList);
    }

    public Collection<Category> getSubCategoryList(int parentId) {
        final String key = SUB_CATEGORY_KEY + ":" + parentId;
        Map<String, Category> map = (Map<String, Category>)redisService.hmget(key);
        return map.values();
    }

    public void cacheCategoryGoodsPage(int categoryId,int page,List<Goods> goodsList){
        final String key = CATEGORY_GOODS_PAGE_KEY + ":" + categoryId;
        List<String> keys = new ArrayList<>();
        keys.add(key);
        redisService.eval(cacheGoodsMapScript,keys,"","",GoodsConstant.CATEGORY_GOODS_PAGE_EXPIRE+"");
        redisService.hset(key,page+"",goodsList);
    }

    public List<Goods> getCategoryGoodsPage(int categoryId,int page){
        final String key = CATEGORY_GOODS_PAGE_KEY + ":" + categoryId;
        return (List<Goods>) redisService.hget(key, page + "");
    }

    private Map<String, Object> listToMap(List<Region> list) {
        final int cap = (int) (list.size() / 0.75 + 1);
        Map<String, Object> map = new HashMap<>(cap);
        for (Region region : list) {
            map.put(region.getId().toString(), region);
        }
        return map;
    }

    private Map<String, Object> listToMap2(List<Category> list) {
        final int cap = (int) (list.size() / 0.75 + 1);
        Map<String, Object> map = new HashMap<>(cap);
        for (Category category : list) {
            map.put(category.getId().toString(), category);
        }
        return map;
    }


    private Map<String, Object> listToMap3(List<Goods> list) {
        final int cap = (int) (list.size() / 0.75 + 1);
        Map<String, Object> map = new HashMap<>(cap);
        for (Goods goods : list) {
            map.put(goods.getId().toString(), goods);
        }
        return map;
    }
}
