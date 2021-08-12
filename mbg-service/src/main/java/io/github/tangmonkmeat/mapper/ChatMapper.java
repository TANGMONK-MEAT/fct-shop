package io.github.tangmonkmeat.mapper;

import io.github.tangmonkmeat.model.Chat;
import io.github.tangmonkmeat.model.ChatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChatMapper {
    long countByExample(ChatExample example);

    int deleteByExample(ChatExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Chat record);

    int insertSelective(Chat record);

    List<Chat> selectByExample(ChatExample example);

    Chat selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Chat record, @Param("example") ChatExample example);

    int updateByExample(@Param("record") Chat record, @Param("example") ChatExample example);

    int updateByPrimaryKeySelective(Chat record);

    int updateByPrimaryKey(Chat record);

    List<Integer> getChatIdByUser(String openId);

    int updateChatStatus(@Param("chatId") Integer chatId,@Param("isDeleted") Boolean isDeleted);

    int updateShowToBoth(int chatId);

    Integer getChatIdByChat(@Param("chat") Chat chat);
}