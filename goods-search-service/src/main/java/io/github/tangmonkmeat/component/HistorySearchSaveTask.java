package io.github.tangmonkmeat.component;

import io.github.tangmonkmeat.cache.SearchKeywordCacheManager;
import io.github.tangmonkmeat.mapper.UserMapper;
import io.github.tangmonkmeat.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/21 下午4:26
 */
@Component
public class HistorySearchSaveTask {

    @Autowired
    private SearchService searchService;

    @Autowired
    private SearchKeywordCacheManager searchKeywordCacheManager;


    private final Logger LOGGER = LoggerFactory.getLogger(HistorySearchSaveTask.class);

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 统计每天的1点30分点前的搜索量，入库
     *
     */
    @Scheduled(cron = "0 30 1 * * ?")
    public void saveGoodsBrowseNum(){
        LOGGER.info("开始保存用户的搜索历史");
        Set<String> userList = searchKeywordCacheManager.getSearchUserList();
        for(String openid : userList){
            Set<String> history = searchKeywordCacheManager.getUserSearchHistory(openid, 15);
            for (String keyword : history){
                searchService.updateUserHistory(openid,keyword);
            }
            searchKeywordCacheManager.clearSearchHistory(openid);
        }

        // 清理缓存
        searchKeywordCacheManager.clearSearchUserList();
        LOGGER.info("保存用户的搜索历史成功");
    }
}
