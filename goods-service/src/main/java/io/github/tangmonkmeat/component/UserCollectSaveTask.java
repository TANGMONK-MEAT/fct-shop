package io.github.tangmonkmeat.component;

import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.exception.BusinessException;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.service.UserService;
import io.github.tangmonkmeat.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: 用户收藏数据入库的定时任务
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/23 下午9:10
 */
@Component
public class UserCollectSaveTask {

    @Autowired
    private RedisService redisService;

    @Resource
    private UserService userService;

    private final Logger LOGGER = LoggerFactory.getLogger(UserCollectSaveTask.class);

    private final String USER_COLLECT_GOODS_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.USER_COLLECT_GOODS_KEY,"openId");

    private final String COLLECT_GOODS_USER_SET_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.COLLECT_GOODS_USER_SET_KEY,"userSet");

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 统计每天的1点30分点前的用户收藏数据，收藏量入库
     *
     */
    @Scheduled(cron = "0 30 1 * * ?")
    public void saveGoodsBrowseNum() throws BusinessException {
        LOGGER.info("开始缓存用户的收藏数据");
        Set<String> openIdList = redisService.zrange(COLLECT_GOODS_USER_SET_KEY, 0, -1);
        try {
            openIdList.forEach(openId -> {
                String key = USER_COLLECT_GOODS_KEY + ":" + openId;
                Map<String, Integer> collectMap = (Map<String, Integer>) redisService.hmget(key);
                List<Long> delList = new ArrayList<>();
                List<Long> addList = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : collectMap.entrySet()) {
                    Integer type = entry.getValue();
                    String goodsId = entry.getKey();
                    if (type == 1) {
                        addList.add(Long.valueOf(goodsId));
                    } else if (type == 0){
                        delList.add(Long.valueOf(goodsId));
                    }
                }
                // 入库
                if (!CollectionUtils.isEmpty(addList)){
                    userService.collectAdd(openId,addList);
                }
                if (!CollectionUtils.isEmpty(delList)) {
                    userService.collectDelete(openId, delList);
                }
                // 清理收藏数据缓存
                redisService.del(key);
                LOGGER.info("缓存用户的收藏数据成功，openId: {}, collectMap: {}",openId,collectMap);
            });
            // 清理收藏用户缓存
            redisService.del(COLLECT_GOODS_USER_SET_KEY);
            LOGGER.info("缓存用户的收藏数据全部成功");
        }catch (Exception e){
            throw new BusinessException(ResponseStatus.SAVE_COLLECT_GOODS_WRONG.errno(),
                    ResponseStatus.SAVE_COLLECT_GOODS_WRONG.errmsg());
        }
    }
}
