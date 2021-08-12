package io.github.tangmonkmeat.service;

import io.github.tangmonkmeat.model.EsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Description:
 *
 * @author zwl
 * @date 2021/7/18 下午5:09
 * @version 1.0
 */
public interface EsProductService {

    /**
     * 从数据库中导入所有商品到ES
     */
    int importAll();

    /**
     * 根据id删除商品
     */
    void delete(Long id);

    /**
     * 根据id创建商品
     */
    EsProduct create(Long id);

    /**
     * 批量删除商品
     */
    void delete(List<Long> ids);

    /**
     * 根据关键字搜索名称或者副标题
     */
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据关键字搜索名称或者商品描述复合查询
     */
    Page<EsProduct> search(String keyword, Long categoryId, Integer pageNum, Integer pageSize,Integer sort);

    /**
     * 根据商品id推荐相关商品
     */
    Page<EsProduct> recommend(Long id, Integer pageNum, Integer pageSize);
}
