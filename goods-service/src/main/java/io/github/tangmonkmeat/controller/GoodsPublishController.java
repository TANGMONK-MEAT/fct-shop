package io.github.tangmonkmeat.controller;

import io.github.tangmonkmeat.Enum.MqMsgType;
import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.cache.GoodsCacheManager;
import io.github.tangmonkmeat.dto.JwtUser;
import io.github.tangmonkmeat.dto.MqMessage;
import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.model.po.PostExample;
import io.github.tangmonkmeat.model.Category;
import io.github.tangmonkmeat.model.Region;
import io.github.tangmonkmeat.mq.EsProductSender;
import io.github.tangmonkmeat.service.GoodsPublishService;
import io.github.tangmonkmeat.token.injection.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 下午4:29
 */
@RestController
@RequestMapping("/post")
public class GoodsPublishController {

    private final Logger LOGGER = LoggerFactory.getLogger(GoodsPublishController.class);

    @Autowired
    private GoodsPublishService goodsPublishService;

    @Autowired
    private GoodsCacheManager goodsCacheManager;

    @Autowired
    private EsProductSender esProductSender;

    /**
     * 商品发布接口
     *
     * @param user JwtUser
     * @param post PostExample
     * @return 信息不完整返回 ResponseStatus.POST_INFO_INCOMPLETE
     */
    @PostMapping(value = "/post")
    public Response<Object> publishGoods(@Jwt() JwtUser user,
                                         @RequestBody PostExample post) {

        if (StringUtils.isEmpty(post.getName()) ||
                StringUtils.isEmpty(post.getDesc()) ||
                StringUtils.isEmpty(post.getRegion()) ||
                post.getCategoryId() == null ||
                post.getRegionId() == null ||
                post.getPrice() == null ||
                post.getImages() == null || post.getImages().size() < 1) {
            String msg = "用户发布商品失败，信息不完整";
            LOGGER.info(msg);
            return Response.fail(ResponseStatus.POST_INFO_INCOMPLETE, msg);
        }
        post.setSellerId(user.getOpenId());
        // 1 保存到 db
        long goodsId = goodsPublishService.postGoods(post);
        // 保存成功？
        if (goodsId > 0) {
            // 2 缓存到redis
            goodsCacheManager.cacheGoodsInfoAsMap(goodsId);
            // 3 通过消息队列，将商品消息提供给【goods-search-service】
            esProductSender.sendSaveMessage(new MqMessage<>(MqMsgType.ES_PRODUCT_SAVE.getType(), goodsId, ""));
            LOGGER.info("用户发布商品：用户id=【{}】，商品名=【{}】，{}张图片",
                    user.getOpenId(), post.getName(), post.getImages().size());
        } else {
            LOGGER.info("用户发布商品失败：用户id=【{}】，商品名=【{}】，{}张图片",
                    user.getOpenId(), post.getName(), post.getImages().size());
        }
        return Response.ok(null);
    }

    /**
     * 删除用户发布的商品的接口
     *
     * @param goodsId 商品ID
     * @param user    JwtUser
     * @return 如果商品信息不匹配返回 ResponseStatus.SELLER_AND_GOODS_IS_NOT_MATCH
     */
    @PostMapping(value = "/delete/{goodsId}")
    public Response<Object> deletePublished(@PathVariable("goodsId") int goodsId,
                                            @Jwt JwtUser user) {
        int row = 0;
        try {
            // 1 del db
            row = goodsPublishService.deleteGoods(goodsId, user.getOpenId());
        } catch (Exception e) {
            LOGGER.error("", e);
            String errmsg = ResponseStatus.SELLER_AND_GOODS_IS_NOT_MATCH.errmsg();
            if (e.getMessage().equals(errmsg)) {
                return Response.fail(ResponseStatus.SELLER_AND_GOODS_IS_NOT_MATCH);
            }
        }
        if (row > 0) {
            // 2 del redis
            goodsCacheManager.removeGoodsInfo((long) goodsId);
            // 3 del es
            esProductSender.sendDelMessage(new MqMessage<>(MqMsgType.ES_PRODUCT_DEL.getType(), goodsId, ""));
            LOGGER.info("用户删除商品: 用户id=【{}】，商品Id=【{}】", user.getOpenId(), goodsId);
        }
        LOGGER.info("用户删除商品失败: 用户id=【{}】，商品Id=【{}】", user.getOpenId(), goodsId);
        return Response.ok(null);
    }

    /**
     * 获取地域信息列表
     *
     * @param regionId 地域编号
     * @return 地域信息列表
     */
    @GetMapping("/region/{regionId}")
    public Response<Object> getRegion(@PathVariable("regionId") int regionId) {
        Collection<Object> regionList1 = goodsCacheManager.getRegionList(regionId);
        if (!CollectionUtils.isEmpty(regionList1)) {
            LOGGER.info("命中cache，通过地区id=【{}】，搜索地区子列表。搜索到{}个结果", regionId, regionList1.size());
            return Response.ok(regionList1);
        }
        List<Region> regionList = goodsPublishService.getRegionList(regionId);
        goodsCacheManager.cacheRegionList(regionId, regionList);
        LOGGER.info("命中DB，通过地区id=【{}】，搜索地区子列表。搜索到{}个结果", regionId, regionList.size());
        return Response.ok(regionList);
    }

    /**
     * 获取发布商品时需要填选的分类
     *
     * @param cateId 分类id
     * @return 分类列表
     */
    @GetMapping("/category/{cateId}")
    public Response<Object> getPostCateList(@PathVariable("cateId") int cateId) {
        Collection<Category> categoryList = goodsCacheManager.getCategoryList(cateId);
        if (!CollectionUtils.isEmpty(categoryList)) {
            LOGGER.info("命中cache，通过分类id=【{}】，搜索分类子列表。搜索到{}个结果", cateId, categoryList.size());
            return Response.ok(categoryList);
        }
        List<Category> cateList = goodsPublishService.getCateList(cateId);
        goodsCacheManager.cacheCategoryList(cateId, cateList);
        LOGGER.info("命中DB，通过分类id=【{}】，搜索分类子列表。搜索到{}个结果", cateId, cateList.size());
        return Response.ok(cateList);
    }
}
