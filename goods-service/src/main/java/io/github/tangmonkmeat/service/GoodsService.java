package io.github.tangmonkmeat.service;

import io.github.tangmonkmeat.model.Category;
import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.model.GoodsComment;
import io.github.tangmonkmeat.model.GoodsGallery;
import io.github.tangmonkmeat.model.vo.CommentVo;

import java.util.List;

public interface GoodsService {

    List<Integer> getGoodsIdList();

    void saveGoodsBrowseNum(int num,int id);

    List<Goods> getGoodsRelated(Long goodsId, int page, int size);

    Goods getGoodsByGoodsId(Long goodsId);

    List<Goods> getGoodsListByCateId(int cateId,int page,int size);

    List<Category> getBrotherCategoryByCateId(int cateId);

    int getParentIdByCateId(int cateId);

    int getSellerHistory(String openId);

    List<GoodsGallery> getGoodsGallery(Long goodsId);

    List<CommentVo> getGoodsComment(Long goodsId);

    void addComment(GoodsComment goodsComment);

}
