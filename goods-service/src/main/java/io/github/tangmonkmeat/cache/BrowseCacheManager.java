package io.github.tangmonkmeat.cache;

import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import io.github.tangmonkmeat.service.GoodsService;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: 点击量缓存
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 上午8:52
 */
@Component
public class BrowseCacheManager {

    @Autowired
    private RedisService redisService;

    private final String GOODS_BROWSE_NUM_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.GOODS_BROWSE_NUM_KEY,"goodsId");

    private final String GOODS_BROWSE_NUM_OF_MONTH_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.GOODS_BROWSE_NUM_OF_MONTH_KEY,"goodsId");

    /**
     * 缓存商品的点击过程量, 数据结构 ZSET
     *
     * key(goodsId) score(时间戳) member(ip)
     *
     * ip作为value，保证每个ip每天只计入一次点击量
     *
     * @param id 商品id
     */
    public void cacheGoodsBrowseNum(String id,Long time,String ip){
        final String key = GOODS_BROWSE_NUM_KEY + ":" + id;
        redisService.zadd(key,time,ip);
    }

    /**
     * 缓存商品今天的点击的结果量，只保留前30天的数据，数据结构 list
     *
     * key(goodsId) value(last_1(num),last_2(num),last_3(num)...last_30,last_30)
     *
     * @param id 商品id
     */
    public void cacheGoodsBrowseOfMonth(Long id){
        final String key0 = GOODS_BROWSE_NUM_OF_MONTH_KEY + ":" + id;
        final String key1 = GOODS_BROWSE_NUM_KEY + ":" + id;
        long now = System.currentTimeMillis();
        long cap = 86400000;
        // 统计24小时内的点击量
        Long browseNum = redisService.zcount(key1, now - cap, now);
        // 队列开头为最新点击量
        redisService.lpush(key0,browseNum.toString());
        // 截取，只保留30天内的
        redisService.lTrim(key0,0,29);
    }

    public Long getGoodsBrowseCount(Long id){
        final String key = GOODS_BROWSE_NUM_KEY + ":" + id;
        return redisService.zsize(key);
    }
}
