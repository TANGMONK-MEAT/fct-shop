package io.github.tangmonkmeat.model.vo;

import io.github.tangmonkmeat.model.EsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Description: 搜索的商品列表
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/19 上午10:02
 */
public class SearchGoodsPageVO {

    private Integer total;

    private Integer pageNum;

    private List<EsProduct> goods;

    @Override
    public String toString() {
        return "SearchGoodsPageVO{" +
                "total=" + total +
                ", pageNum=" + pageNum +
                ", goods=" + goods +
                '}';
    }

    public SearchGoodsPageVO(){}

    public SearchGoodsPageVO(Page<EsProduct> page){
        List<EsProduct> content = page.getContent();
        this.total = content.size();
        this.pageNum = page.getNumber();
        this.goods = page.getContent();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public List<EsProduct> getGoods() {
        return goods;
    }

    public void setGoods(List<EsProduct> goods) {
        this.goods = goods;
    }
}
