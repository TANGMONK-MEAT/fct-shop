package io.github.tangmonkmeat.controller;

import io.github.tangmonkmeat.Enum.CategoryEnum;
import io.github.tangmonkmeat.cache.GoodsCacheManager;
import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.model.Ad;
import io.github.tangmonkmeat.model.Category;
import io.github.tangmonkmeat.model.Channel;
import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.model.vo.CatalogPageVo;
import io.github.tangmonkmeat.model.vo.IndexPageVo;
import io.github.tangmonkmeat.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/22 上午10:29
 */
@RestController
public class IndexController {

    private final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IndexService indexService;

    @Autowired
    private GoodsCacheManager goodsCacheManager;

    /**
     * 展示首页,包括:广告,首页分类,首页商品
     */
    @GetMapping("/index/index")
    public Response<Object> index(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {

        IndexPageVo vo = new IndexPageVo();

        // goodsList
        List<Goods> goodsList = goodsCacheManager.getGoodsInfoListRange((page - 1) * size, (page - 1) * size + size - 1);
        if (CollectionUtils.isEmpty(goodsList) || goodsList.size() < size) {
            goodsList = indexService.getIndexMore(page, size);
            goodsCacheManager.cacheGoodsInfoList(goodsList);
        }
        vo.setIndexGoodsList(goodsList);

        // adList
        List<Ad> adList = goodsCacheManager.getIndexAdList();
        if (CollectionUtils.isEmpty(adList)) {
            adList = indexService.getAdList();
            goodsCacheManager.cacheIndexAdList(adList);
        }
        vo.setBanner(adList);

        // channelList
        List<Channel> channelList = goodsCacheManager.getIndexChannelList();
        if (CollectionUtils.isEmpty(channelList)) {
            channelList = indexService.getChannelList();
            goodsCacheManager.cacheIndexChannelList(channelList);
        }
        vo.setChannel(channelList);

        LOGGER.info("浏览首页 : 展示{}个广告, {}个分类, {}个商品", vo.getBanner().size(), vo.getChannel().size(), vo.getIndexGoodsList().size());
        return Response.ok(vo);
    }

    /**
     * 首页展示更多推荐商品
     *
     * @param page 当前页
     * @param size 商品个数
     * @return goods-list
     */
    @GetMapping("/index/more")
    public Response<List<Goods>> indexMore(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {

        List<Goods> goodsList;

        goodsList = goodsCacheManager.getGoodsInfoListRange((page - 1) * size, (page - 1) * size + size - 1);
        if (CollectionUtils.isEmpty(goodsList) || goodsList.size() < size) {
            goodsList = indexService.getIndexMore(page, size);
            goodsCacheManager.cacheGoodsInfoList(goodsList);
        }
        LOGGER.info("首页获取更多推荐商品 : 返回{}个商品", goodsList.size());
        return Response.ok(goodsList);
    }

    /**
     * 分类页,展示:所有主分类,排名第一的主分类包含的子分类
     *
     * @return CatalogPageVo
     */
    @GetMapping("/catalog/index")
    public Response<CatalogPageVo> catalog() {
        CatalogPageVo vo = new CatalogPageVo();
        // 从 cache 获取 mainList
        Collection<Category> mainCates = goodsCacheManager.getCategoryList(CategoryEnum.ONE.getParentId());
        if (!CollectionUtils.isEmpty(mainCates)) {
            vo.setAllCategory(new ArrayList<>(mainCates));
            Category category = vo.getAllCategory().get(0);
            // 从 cache 获取 subList
            Collection<Category> subList = goodsCacheManager.getSubCategoryList(category.getId());
            if (!CollectionUtils.isEmpty(subList)) {
                vo.setSubCategory(new ArrayList<>(subList));
            } else {
                Integer id = category.getId();
                List<Category> subList1 = indexService.getSubCatalogById(id);
                goodsCacheManager.cacheSubCategoryList(id, subList1);
                vo.setSubCategory(new ArrayList<>(subList1));
            }
        } else {
            vo = indexService.getCatalogIndex();
            // 缓存 分类信息
            List<Category> allCategory = vo.getAllCategory();
            goodsCacheManager.cacheCategoryList(CategoryEnum.ONE.getParentId(), allCategory);
            goodsCacheManager.cacheSubCategoryList(allCategory.get(0).getId(), vo.getSubCategory());
        }

        LOGGER.info("浏览分类页 : 展示{}个主分类, 展示{}个子分类", vo.getAllCategory().size(), vo.getSubCategory().size());
        return Response.ok(vo);
    }

    /**
     * 分类页, 获取选定主分类下的子分类
     *
     * @param id 分类id
     * @return Category-list
     */
    @GetMapping("/catalog/{id}")
    public Response<List<Category>> subCatalog(@PathVariable("id") int id) {
        List<Category> res;
        Collection<Category> subList = goodsCacheManager.getSubCategoryList(id);
        if (!CollectionUtils.isEmpty(subList)) {
            res = new ArrayList<>(subList);
        } else {
            res = indexService.getSubCatalogById(id);
            goodsCacheManager.cacheSubCategoryList(id, res);
        }
        LOGGER.info("浏览分类页,筛选分类 : 展示{}个子分类", res.size());
        return Response.ok(res);
    }
}
