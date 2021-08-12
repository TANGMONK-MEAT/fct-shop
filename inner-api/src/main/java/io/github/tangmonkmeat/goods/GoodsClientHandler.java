package io.github.tangmonkmeat.goods;

import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.dto.SimpleGoods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/23 下午7:40
 */
@Component
public class GoodsClientHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(GoodsClientHandler.class);

    @Autowired
    private GoodsClient goodsClient;

    public SimpleGoods getSimpleGoods(Long goodsId) {
        LOGGER.info("从商品服务查询商品的简单信息");
        Response<SimpleGoods> response = goodsClient.getSimpleGoods(goodsId);
        if (response.getErrno() != 0) {
            LOGGER.info("从商品服务获取商品信息列表失败,errno={},原因={}", response.getErrno(), response.getErrmsg());
            return SimpleGoods.unknownGoods();
        }
        return response.getData();
    }

    public Map<Long, SimpleGoods> getSimpleGoodsList(List<Long> goodsIdList) {
        LOGGER.info("从商品服务查询商品的简单信息");
        if (goodsIdList == null || goodsIdList.size() < 1) {
            LOGGER.info("商品idList为空,返回空的结果");
            return new HashMap<>(0);
        }
        Response<Map<Long, SimpleGoods>> response = goodsClient.getSimpleGoodsList(goodsIdList);
        if (response.getErrno() != 0) {
            LOGGER.info("从商品服务获取商品信息列表失败,errno={},原因={}", response.getErrno(), response.getErrmsg());
            return new HashMap<>(0);
        }
        return response.getData();
    }

}
