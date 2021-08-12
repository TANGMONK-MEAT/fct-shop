package io.github.tangmonkmeat.mapper;

import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.model.GoodsExample;
import io.github.tangmonkmeat.model.User;
import io.github.tangmonkmeat.model.UserExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    int setUserCollect(@Param("userId") String userId, @Param("goodsId") Long goodsId);

    int deleteUserCollect(@Param("userId") String userId, @Param("goodsId") Long goodsId);

    boolean userHasCollect(@Param("userId") String openId,@Param("goodsId") Long goodsId);

    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    List<SimpleUser> selectByOpenIdList(List<String> openIdList);

    User selectByPrimaryKey(Integer id);

    User selectByOpenId(String openId);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<String> selectAllOpenIds();

    List<Goods> getUserCollect(String openId);

    List<Goods> getUserBought(String openId);

    List<Goods> getUserSold(String openId);

    List<Goods> getUserPosted(String openId);

    List<GoodsExample> getUserHistoryList(String openId);

    boolean userHasWant(@Param("sellerId") String sellerId, @Param("goodsId") int goodsId);

    void setUserWant(@Param("sellerId") String sellerId, @Param("goodsId") int goodsId);
}