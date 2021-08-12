package io.github.tangmonkmeat.mapper;

import io.github.tangmonkmeat.model.SearchHistory;
import io.github.tangmonkmeat.model.SearchHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

public interface SearchHistoryMapper {
    long countByExample(SearchHistoryExample example);

    int deleteByExample(SearchHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SearchHistory record);

    int insertSelective(SearchHistory record);

    List<SearchHistory> selectByExample(SearchHistoryExample example);

    SearchHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SearchHistory record, @Param("example") SearchHistoryExample example);

    int updateByExample(@Param("record") SearchHistory record, @Param("example") SearchHistoryExample example);

    int updateByPrimaryKeySelective(SearchHistory record);

    int updateByPrimaryKey(SearchHistory record);

    boolean isExistedHistory(@Param("userId") String userId,@Param("keyword") String keyword);

    int updateSearchTime(@Param("userId") String userId,@Param("keyword") String keyword);
}