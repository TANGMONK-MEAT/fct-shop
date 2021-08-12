package io.github.tangmonkmeat.cache;

import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * Description: 搜索关键字缓存管理器
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 下午3:39
 */
@Component
public class SearchKeywordCacheManager {

    private final Logger LOGGER = LoggerFactory.getLogger(SearchKeywordCacheManager.class);

    @Autowired
    private RedisService redisService;

    private final String GOODS_SEARCH_KEYWORD_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.GOODS_SEARCH_KEYWORD_KEY,"user_keyword");

    private final String GOODS_HOT_SEARCH_KEYWORD_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.GOODS_SEARCH_KEYWORD_KEY,"hot_keyword");

    private final String GOODS_SEARCH_USER_LIST_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.GOODS_SEARCH_USER_LIST_KEY,"userList");

    /**
     * 每个用户最多只缓存 15个关键字
     */
    private final int USER_MAX_QUEUE_LEN = 15;

    /**
     * 热门关键字。只缓存30个
     */
    private final int HOT_MAX_QUEUE_LEN = 30;

    public void cacheGoodsSearchKeyword(String openId,String keyword){
        final String key = GOODS_SEARCH_KEYWORD_KEY + ":" + openId;

        // 缓存用户的搜索历史
        if (!StringUtils.isEmpty(openId)) {
            // 缓存用户的搜索总次数
            redisService.zincrby(GOODS_SEARCH_USER_LIST_KEY,1,openId);
            // 缓存用户的每个搜索的次数
            final Long size = redisService.zsize(key);
            if (size >= USER_MAX_QUEUE_LEN) {
                redisService.zremByRange(key, USER_MAX_QUEUE_LEN - 1, size - 1);
            }
            Double count = redisService.zincrby(key, 1, keyword);
            LOGGER.debug("search keyword Cache更新关键字【{}】的搜索次数为【{}】", keyword, count);
        }

        // 缓存热门搜索
        final Long hotSize = redisService.zsize(GOODS_HOT_SEARCH_KEYWORD_KEY);
        if (hotSize >= HOT_MAX_QUEUE_LEN){
            redisService.zremByRange(GOODS_HOT_SEARCH_KEYWORD_KEY, USER_MAX_QUEUE_LEN - 1, hotSize - 1);
        }
        Double count = redisService.zincrby(GOODS_HOT_SEARCH_KEYWORD_KEY, 1, keyword);
        LOGGER.debug("hot search keyword Cache更新关键字【{}】的搜索次数为【{}】", keyword, count);
    }

    public void clearUserSearchHistory(String openId){
        final String key = GOODS_SEARCH_KEYWORD_KEY + ":" + openId;
        redisService.zremByRange(key,0,-1);
    }

    public Set<String> getSearchUserList(){
        return  redisService.zrange(GOODS_SEARCH_USER_LIST_KEY, 0, -1);
    }

    public void clearSearchUserList(){
        redisService.zremByRange(GOODS_SEARCH_USER_LIST_KEY,0,-1);
    }

    /**
     * 获取指定数量的热门关键字
     *
     * @param num 限定
     * @return list
     */
    public Set<String> getUserSearchHistory(String openId,long num){
        final String key = GOODS_SEARCH_KEYWORD_KEY + ":" + openId;
        LOGGER.debug("从goods search keyword Cache获取热门关键字列表");
        return redisService.zrange(key, 0, num);
    }

    /**
     * 利用redis zrevrange 命令，
     *
     * 获取指定区间的热门keyword, 按照score从大到小，获取[start,end] 区间内的keyword
     *
     * @param start start
     * @param end end
     * @return list
     */
    public Set<String> getHot(long start,long end){
        return redisService.zrevrange(GOODS_HOT_SEARCH_KEYWORD_KEY,start,end);
    }

    public void clearSearchHistory(String openId){
        final String key = GOODS_SEARCH_KEYWORD_KEY + ":" + openId;
        redisService.zremByRange(key,0,-1);
    }
}
