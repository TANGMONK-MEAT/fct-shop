package io.github.tangmonkmeat.goods;

import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.dto.SimpleGoods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Description: goods-service的内部api
 *
 * @author zwl
 * @date 2021/7/23 下午7:34
 * @version 1.0
 */
@FeignClient(name = "goods-service")
@RequestMapping(value = "/goods-service")
public interface GoodsClient {

    @GetMapping("/simpleGoods/{goodsId}")
    Response<SimpleGoods> getSimpleGoods(@PathVariable("goodsId") Long goodsId);


    @GetMapping("/simpleGoodsList")
    Response<Map<Long, SimpleGoods>> getSimpleGoodsList(@RequestParam List<Long> goodsIdList);

}
