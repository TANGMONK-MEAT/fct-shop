package io.github.tangmonkmeat.mapper;

import io.github.tangmonkmeat.model.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 *
 * @author zwl
 * @date 2021/7/18 下午3:26
 * @version 1.0
 */
public interface EsProductMapper {

    EsProduct getEsProductByPrimaryId(int id);

    List<EsProduct> getAllEsProductList(@Param("start") Integer start, @Param("size") Integer size);
}
