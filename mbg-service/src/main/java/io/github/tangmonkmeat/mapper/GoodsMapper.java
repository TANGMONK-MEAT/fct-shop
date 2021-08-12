package io.github.tangmonkmeat.mapper;

import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.model.GoodsComment;
import io.github.tangmonkmeat.model.GoodsGallery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {

    List<Goods> selectSimpleGoods();

    List<Goods> selectGoodsByCateId(@Param("cateId") int cateId);

    int selectSellerHistory(String sellerId);

    Goods selectSimpleGoodsById(Long goodsId);

    List<Goods> selectSimpleGoodsList(@Param("goodsIdList") List<Long> goodsIdList);

    List<Goods> selectSimpleGoodsInSameCate(Long goodsId);

    List<Goods> selectSimpleGoodsInSameParentCate(Long goodsId);

    List<GoodsGallery> selectGalleryByGoodsId(Long goodsId);

    List<GoodsComment> selectBaseCommentById(Long goodsId);

    List<GoodsComment> selectReplyComment(Integer replyId);

    int deleteByPrimaryKey(Integer id);

    long insert(Goods record);

    long insertSelective(Goods record);

    int updateGoodsBrowseCount(@Param("browseCount") Integer browseCount,@Param("id") Integer id);

    boolean validateSeller(@Param("goodsId") Integer goodsId,@Param("sellerId") String sellerId);

    List<Integer> selectGoodsIds();

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);
}