package io.github.tangmonkmeat.service.impl;

import io.github.tangmonkmeat.mapper.SearchHistoryMapper;
import io.github.tangmonkmeat.model.SearchHistory;
import io.github.tangmonkmeat.service.SearchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/21 下午4:31
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private SearchHistoryMapper searchHistoryMapper;

    @Override
    public void updateUserHistory(String openId, String keyword) {
        if (searchHistoryMapper.isExistedHistory(openId,keyword)){
            searchHistoryMapper.updateSearchTime(openId,keyword);
        }else {
            SearchHistory history = new SearchHistory();
            history.setUserId(openId);
            history.setKeyword(keyword);
            searchHistoryMapper.insert(history);
        }
    }
}
