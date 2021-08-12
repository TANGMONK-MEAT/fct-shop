package io.github.tangmonkmeat.controller;

import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.cache.BrowseCacheManager;
import io.github.tangmonkmeat.cache.GoodsCacheManager;
import io.github.tangmonkmeat.dto.JwtUser;
import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.model.Category;
import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.model.GoodsComment;
import io.github.tangmonkmeat.model.GoodsGallery;
import io.github.tangmonkmeat.model.vo.CategoryPageVo;
import io.github.tangmonkmeat.model.vo.CommentVo;
import io.github.tangmonkmeat.model.vo.GoodsDetailPageVo;
import io.github.tangmonkmeat.service.GoodsService;
import io.github.tangmonkmeat.service.UserService;
import io.github.tangmonkmeat.token.injection.Jwt;
import io.github.tangmonkmeat.user.UserClientHandler;
import io.github.tangmonkmeat.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Description: 商品浏览相关API
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/22 下午8:45
 */
@RestController
@RequestMapping(value = "/goods")
public class GoodsController {

    private final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsCacheManager goodsCacheManager;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @Autowired
    private BrowseCacheManager browseCacheManager;

    @Autowired
    private UserClientHandler userClientHandler;

    /**
     * 通过分类浏览商品,获取选定目录下的商品列表和同级的兄弟目录
     *
     * @param categoryId 分类id
     * @return CategoryPageVo
     */
    @GetMapping("/category/index/{categoryId}")
    public Response<CategoryPageVo> getCategoryPage(@PathVariable("categoryId") int categoryId,
                                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        CategoryPageVo vo = new CategoryPageVo();

        // goodsList
        List<Goods> goodsList = goodsCacheManager.getCategoryGoodsPage(categoryId, page);
        if (goodsList == null) {
            goodsList = goodsService.getGoodsListByCateId(categoryId,page,size);
            goodsCacheManager.cacheCategoryGoodsPage(categoryId, page, goodsList);
        }
        vo.setGoodsList(goodsList);

        // brotherCategory
        int cateParentId = goodsService.getParentIdByCateId(categoryId);
        Collection<Category> categoryList = goodsCacheManager.getCategoryList(cateParentId);
        if (CollectionUtils.isEmpty(categoryList)) {
            categoryList = goodsService.getBrotherCategoryByCateId(categoryId);
            goodsCacheManager.cacheCategoryList(cateParentId, new ArrayList<>(categoryList));
        }
        vo.setBrotherCategory(new ArrayList<>(categoryList));
        return Response.ok(vo);
    }


    /**
     * 通过分类浏览商品,获取选定目录下的商品列表
     *
     * @param categoryId 分类id
     * @param page       页码
     * @param size       限定个数
     * @return Goods
     */
    @GetMapping("/category/{categoryId}")
    public Response<List<Goods>> getGoodsByCategory(@PathVariable("categoryId") int categoryId,
                                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        // goodsList
        List<Goods> goodsList = goodsCacheManager.getCategoryGoodsPage(categoryId, page);
        if (goodsList == null) {
            goodsList = goodsService.getGoodsListByCateId(categoryId,page,size);
            goodsCacheManager.cacheCategoryGoodsPage(categoryId, page, goodsList);
        }
        return Response.ok(goodsList);
    }

    /**
     * 获取商品的详细信息,包括:商品信息,商品图片,商品评论,卖家信息,用户是否收藏了该商品
     *
     * @param goodsId 商品ID
     * @param jwtUser JwtUser
     * @return GoodsDetailPageVo
     */
    @GetMapping("/detail/{goodsId}")
    public Response<GoodsDetailPageVo> getGoodsDetail(@PathVariable("goodsId") Long goodsId,
                                                      @Jwt JwtUser jwtUser,
                                                      HttpServletRequest request) {
        String ip = IpUtil.getIp(request);
        String openId = jwtUser.getOpenId();
        // 缓存点击量
        browseCacheManager.cacheGoodsBrowseNum(goodsId+"", System.currentTimeMillis(), ip);
        // 获取商品详情
        Goods goods = goodsCacheManager.getGoodsDetail(goodsId);
        if (goods == null) {
            goods = goodsService.getGoodsByGoodsId(goodsId);
            goodsCacheManager.cacheGoodsInfoAsMap(goods);
        }
        // 获取卖家信息
        SimpleUser seller = userClientHandler.getSimpleUser(goods.getSellerId());
        if (seller == null) {
            LOGGER.info("搜索goodsId = 【{}】的详情时出错", goodsId);
            return Response.fail(ResponseStatus.USER_NOT_ABOUT_GOODS);
        }

        // 在卖的商品数量
        int sellerHistory = goodsService.getSellerHistory(openId);

        // 当前商品的图片
        List<GoodsGallery> galleryList = goodsService.getGoodsGallery(goodsId);

        // 获取评论
        List<CommentVo> goodsComment = goodsService.getGoodsComment(goodsId);

        // 是否收藏
        boolean userHasCollect = false;
        if (!ObjectUtils.isEmpty(jwtUser)) {

            userHasCollect = userService.userHasCollect(openId, goodsId);
        }

        GoodsDetailPageVo vo = new GoodsDetailPageVo(goods,
                galleryList, seller, sellerHistory, goodsComment, userHasCollect);
        LOGGER.info("浏览商品详情 : 商品id={}，商品名={}", vo.getInfo().getId(), vo.getInfo().getName());
        return Response.ok(vo);
    }

    /**
     * 获取与id商品相关的商品
     *
     * @param goodsId 商品ID
     * @return Goods-list
     */
    @GetMapping("/related/{goodsId}")
    public Response<List<Goods>> getGoodsRelated(@PathVariable("goodsId") long goodsId,
                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        List<Goods> goodsList = goodsService.getGoodsRelated(goodsId, page, size);
        LOGGER.info("获取与 goodsId=[{}] 相关的商品 : 展示{}个商品", goodsId, goodsList.size());
        return Response.ok(goodsList);
    }

    /**
     * 发表评论
     *
     */
    @PostMapping("/comment/post/{goodsId}")
    public Response<Object> postComment(@PathVariable("goodsId") int goodsId,
                                @RequestBody GoodsComment comment,
                                @Jwt JwtUser user) {
        if (StringUtils.isEmpty(user.getOpenId()) ||
                StringUtils.isEmpty(comment.getReplyUserId()) ||
                StringUtils.isEmpty(comment.getContent()) ||
                comment.getReplyCommentId() == null) {
            String msg = "用户发表评论失败，信息不完整";
            LOGGER.info(msg);
            return Response.fail(ResponseStatus.COMMENT_INFO_INCOMPLETE);
        }
        comment.setUserId(user.getOpenId());
        comment.setGoodsId(goodsId);
        comment.setCreateTime(new Date());
        goodsService.addComment(comment);
        LOGGER.info("用户添加评论：用户昵称=【{}】，回复评论id=【{}】，回复内容=【{}】",
                user.getNickName(), comment.getReplyCommentId(), comment.getContent());
        return Response.ok(null);
    }
}