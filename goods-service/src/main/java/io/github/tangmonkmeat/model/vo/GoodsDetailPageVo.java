package io.github.tangmonkmeat.model.vo;


import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.model.GoodsGallery;

import java.io.Serializable;
import java.util.List;

/**
 * 商品详情页
 *
 * @author nnkwrik
 * @date 18/11/17 21:43
 */
public class GoodsDetailPageVo implements Serializable {

    /**商品详情*/
    private Goods info;
    /**商品图片*/
    private List<GoodsGallery> gallery;

    /**卖家信息*/
    private SimpleUser seller;
    /**卖家出售过的商品数*/
    private Integer sellerHistory;

    /**评论*/
    private List<CommentVo> comment;
    /**用户是否收藏*/
    private Boolean userHasCollect;

    public GoodsDetailPageVo() {
    }

    public GoodsDetailPageVo(Goods info,
                             List<GoodsGallery> gallery,
                             SimpleUser seller,
                             Integer sellerHistory,
                             List<CommentVo> comment, Boolean userHasCollect) {
        this.info = info;
        this.gallery = gallery;
        this.seller = seller;
        this.sellerHistory = sellerHistory;
        this.comment = comment;
        this.userHasCollect = userHasCollect;
    }

    @Override
    public String toString() {
        return "GoodsDetailPageVo{" +
                "info=" + info +
                ", gallery=" + gallery +
                ", seller=" + seller +
                ", sellerHistory=" + sellerHistory +
                ", comment=" + comment +
                ", userHasCollect=" + userHasCollect +
                '}';
    }

    public Goods getInfo() {
        return info;
    }

    public void setInfo(Goods info) {
        this.info = info;
    }

    public List<GoodsGallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<GoodsGallery> gallery) {
        this.gallery = gallery;
    }

    public SimpleUser getSeller() {
        return seller;
    }

    public void setSeller(SimpleUser seller) {
        this.seller = seller;
    }

    public Integer getSellerHistory() {
        return sellerHistory;
    }

    public void setSellerHistory(Integer sellerHistory) {
        this.sellerHistory = sellerHistory;
    }

    public List<CommentVo> getComment() {
        return comment;
    }

    public void setComment(List<CommentVo> comment) {
        this.comment = comment;
    }

    public Boolean getUserHasCollect() {
        return userHasCollect;
    }

    public void setUserHasCollect(Boolean userHasCollect) {
        this.userHasCollect = userHasCollect;
    }
}
