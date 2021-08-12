package io.github.tangmonkmeat.repository;

import io.github.tangmonkmeat.model.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Description: es 操作
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/18 下午3:31
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Integer> {

    /**
     * 搜索查询
     *
     * @param name   商品名称
     * @param region 商品所在地区
     * @param desc   商品描述
     * @param page   分页信息
     * @return EsProduct
     */
    Page<EsProduct> findByNameOrRegionOrDesc(String name, String region, String desc, Pageable page);
}
