package io.github.tangmonkmeat.controller;

import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.dto.SimpleGoods;
import io.github.tangmonkmeat.mapper.GoodsMapper;
import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 对其他服务开放的api
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/23 下午7:12
 */
@RestController
@RequestMapping(value = "/goods-service")
public class GoodsServiceController {

    private final Logger LOGGER = LoggerFactory.getLogger(GoodsServiceController.class);

    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 获取简单商品信息
     *
     * @param goodsId 商品ID
     * @return SimpleGoods
     */
    @GetMapping("/simpleGoods/{goodsId}")
    public Response<SimpleGoods> getSimpleGoods(@PathVariable("goodsId") Long goodsId) {
        Goods goods = goodsMapper.selectSimpleGoodsById(goodsId);
        if (goods == null) {
            LOGGER.info("其他服务通过goodsId : [{}] 查询商品基本信息，没有查询到该商品", goodsId);
            return Response.fail(ResponseStatus.GOODS_IN_NOT_EXIST);
        }

        SimpleGoods dto = new SimpleGoods();
        BeanUtils.copyProperties(goods, dto);
        LOGGER.info("其他服务通过goodsId : [{}] 查询商品基本信息，查询结果={}", goodsId, dto);
        return Response.ok(dto);
    }

    /**
     * 获取简单商品信息列表
     *
     * @param goodsIdList 商品ID列表
     * @return SimpleGoods Map
     */
    @GetMapping("/simpleGoodsList")
    Response<Map<Long, SimpleGoods>> getSimpleGoodsList(@RequestParam List<Long> goodsIdList) {
        List<Goods> goodsList = goodsMapper.selectSimpleGoodsList(goodsIdList);
        int cap = (int) (goodsIdList.size() / 0.75 + 1);
        Map<Long, SimpleGoods> dtoMap = new HashMap<>(cap);
        goodsList.forEach(goods -> {
            SimpleGoods simpleGoods = new SimpleGoods();
            BeanUtils.copyProperties(goods, simpleGoods);
            dtoMap.put(goods.getId(), simpleGoods);
        });

        if (ObjectUtils.isEmpty(dtoMap)) {
            LOGGER.info("其他服务通过goodsId : [{}] 查询商品基本信息，没有查询到该商品", goodsIdList);
            return Response.fail(ResponseStatus.GOODS_IN_NOT_EXIST);
        }

        LOGGER.info("其他服务通过goodsId : [{}] 查询用户基本信息，查询结果：{}", goodsIdList, dtoMap);
        return Response.ok(dtoMap);
    }
}
