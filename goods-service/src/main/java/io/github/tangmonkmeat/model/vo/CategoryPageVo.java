package io.github.tangmonkmeat.model.vo;

import io.github.tangmonkmeat.model.Category;
import io.github.tangmonkmeat.model.Goods;

import java.util.List;

/**
 * Description: 通过分类查询商品时的页面
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/22 上午10:33
 */
public class CategoryPageVo {

    /**同一个父分类下的兄弟分类*/
    private List<Category> brotherCategory;

    /**当前分类的商品列表*/
    private List<Goods> goodsList;

    @Override
    public String toString() {
        return "CategoryPageVo{" +
                "brotherCategory=" + brotherCategory +
                ", goodsList=" + goodsList +
                '}';
    }

    public CategoryPageVo() {
    }

    public CategoryPageVo(List<Category> brotherCategory, List<Goods> goodsList) {
        this.brotherCategory = brotherCategory;
        this.goodsList = goodsList;
    }

    public List<Category> getBrotherCategory() {
        return brotherCategory;
    }

    public void setBrotherCategory(List<Category> brotherCategory) {
        this.brotherCategory = brotherCategory;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
