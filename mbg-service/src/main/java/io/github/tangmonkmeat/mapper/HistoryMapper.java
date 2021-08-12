package io.github.tangmonkmeat.mapper;

import io.github.tangmonkmeat.model.History;
import io.github.tangmonkmeat.model.HistoryExample;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HistoryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(History record);

    int insertSelective(History record);


    History selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(History record);

    int updateByPrimaryKeyWithBLOBs(History record);

    int updateByPrimaryKey(History record);

    List<HistoryExample> getLastReadChat(@Param("unreads") List<Integer> unreadChatIds,
                                         @Param("openId") String openId, @Param("offsetTime") Date offsetTime);

    List<History> selectChatHistory(@Param("chatId") int chatId, @Param("offsetTime") String offsetTime);

    int insertHistoryList(@Param("historyList") List<History> historyList);

    int updateHistoryStatus(@Param("chatId") Integer chatId,@Param("isDeleted") Boolean isDeleted);
}
