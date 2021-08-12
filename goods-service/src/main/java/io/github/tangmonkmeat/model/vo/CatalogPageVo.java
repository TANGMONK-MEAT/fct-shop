package io.github.tangmonkmeat.model.vo;

import io.github.tangmonkmeat.model.Category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description: 分类页
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/22 上午10:34
 */
public class CatalogPageVo {

    /**其他所有和这个同级的分类*/
    private List<Category> allCategory;

    /**这个分类的所有子分类*/
    private List<Category> subCategory;

    @Override
    public String toString() {
        return "CatalogPageVo{" +
                "allCategory=" + allCategory +
                ", subCategory=" + subCategory +
                '}';
    }

    public List<Category> getAllCategory() {
        return allCategory;
    }

    public void setAllCategory(List<Category> allCategory) {
        this.allCategory = allCategory;
    }

    public void setAllCategoryForObj(Collection<Object> allCategory){
        if (allCategory.size() > 0){
            List<Category> categories = new ArrayList<>();
            for (Object obj : allCategory){
                if (obj instanceof Category){
                    categories.add((Category)obj);
                }
            }
            setAllCategory(categories);
        }
    }

    public List<Category> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<Category> subCategory) {
        this.subCategory = subCategory;
    }

    public void setSubCategoryForObj(Collection<Object> subCategory){
        if (subCategory.size() > 0){
            List<Category> categories = new ArrayList<>();
            for (Object obj : subCategory){
                if (obj instanceof Category){
                    categories.add((Category)obj);
                }
            }
            setSubCategory(categories);
        }
    }

    public CatalogPageVo() {
    }

    public CatalogPageVo(List<Category> allCategory, List<Category> subCategory) {
        this.allCategory = allCategory;
        this.subCategory = subCategory;
    }
}
