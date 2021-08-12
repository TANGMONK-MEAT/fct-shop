package io.github.tangmonkmeat.component;

import io.github.tangmonkmeat.cache.BrowseCacheManager;
import io.github.tangmonkmeat.service.GoodsService;
import io.github.tangmonkmeat.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description: 商品点击量定时任务
 *
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 下午2:51
 */
@Component
public class GoodsBrowseNumSaveTask {

    @Autowired
    private BrowseCacheManager browseCacheManager;

    @Autowired
    private GoodsService goodsService;

    private final Logger LOGGER = LoggerFactory.getLogger(GoodsBrowseNumSaveTask.class);

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 统计每天的23点55分点前的点击量，点击量入库
     *
     */
    @Scheduled(cron = "0 55 23 * * ?")
    public void saveGoodsBrowseNum(){
        LOGGER.info("保存每天的商品点击量");
        List<Integer> ids = goodsService.getGoodsIdList();
        for(Integer id : ids){
            Long id0 = (long) id;
            // 点击量递增，入库
            Long add = browseCacheManager.getGoodsBrowseCount(id0);
            goodsService.saveGoodsBrowseNum(add.intValue(),id0.intValue());
            // 统计和缓存今天的点击量，只保留30天内的
            browseCacheManager.cacheGoodsBrowseOfMonth(id0);
        }
        LOGGER.info("保存每天的商品点击量成功");
    }
}
