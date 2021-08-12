package io.github.tangmonkmeat.mapper;

import io.github.tangmonkmeat.model.UserPreference;
import io.github.tangmonkmeat.model.UserPreferenceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPreferenceMapper {
    long countByExample(UserPreferenceExample example);

    int deleteByExample(UserPreferenceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserPreference record);

    int insertSelective(UserPreference record);

    List<UserPreference> selectByExample(UserPreferenceExample example);

    UserPreference selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserPreference record, @Param("example") UserPreferenceExample example);

    int updateByExample(@Param("record") UserPreference record, @Param("example") UserPreferenceExample example);

    int updateByPrimaryKeySelective(UserPreference record);

    int updateByPrimaryKey(UserPreference record);
}