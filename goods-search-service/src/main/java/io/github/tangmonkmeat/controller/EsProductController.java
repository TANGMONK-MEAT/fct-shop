package io.github.tangmonkmeat.controller;

import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.cache.SearchKeywordCacheManager;
import io.github.tangmonkmeat.dto.JwtUser;
import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.model.EsProduct;
import io.github.tangmonkmeat.model.vo.SearchGoodsPageVO;
import io.github.tangmonkmeat.model.SortEnum;
import io.github.tangmonkmeat.model.vo.SearchPageVo;
import io.github.tangmonkmeat.service.EsProductService;
import io.github.tangmonkmeat.token.injection.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Description: 商品搜索管理
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/19 上午8:21
 */
@RestController
@RequestMapping(value = "/search")
public class EsProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(EsProductController.class);

    @Autowired
    private EsProductService esProductService;

    @Autowired
    private SearchKeywordCacheManager searchKeywordCacheManager;

    /**
     * 根据ID删除 ES 中的商品信息
     *
     * @param id 商品ID
     * @return Response
     */
    @GetMapping(value = "/delete/{id}")
    public Response<Object> delete(@PathVariable Long id){
        esProductService.delete(id);
        LOGGER.info("delete EsProduct, id={}",id);
        return Response.ok(null);
    }

    /**
     * 根据 id列表批量删除 ES 商品信息
     *
     * @param ids 商品ID列表
     * @return Response
     */
    @PostMapping(value = "/delete/batch")
    public Response<Object> deleteBatch(@RequestParam("ids") List<Long> ids){
        esProductService.delete(ids);
        LOGGER.info("delete batch EsProduct, ids={}",ids);
        return Response.ok(null);
    }

    /**
     * 根据 关键字简单查询匹配的 ES商品，
     *
     * @param keyword 关键字, 必须
     * @param pageNum 当前页，可选, 必须大于0
     * @param pageSize 每页限定的，可选，最大不可以超过20
     * @return 商品列表
     */
    @GetMapping(value = "/result/{keyword}")
    public Response<Object> simpleSearch(@PathVariable("keyword") String keyword,
                                         @RequestParam(value = "page",defaultValue = "0",required = false) Integer pageNum,
                                         @RequestParam(value = "size",defaultValue = "5",required = false) Integer pageSize,
                                         @Jwt JwtUser user){
        if (pageNum < 0 || pageSize > 20){
            return Response.fail(ResponseStatus.PAGINATION_WRONG);
        }
        // 从es中查询
        Page<EsProduct> esProducts = esProductService.search(keyword, pageNum, pageSize);
        // 缓存keyword
        searchKeywordCacheManager.cacheGoodsSearchKeyword(user.getOpenId(),keyword);
        LOGGER.info("openId={} 的用户查询商品信息，keyword={}", user.getOpenId(),keyword);
        return Response.ok(new SearchGoodsPageVO(esProducts));
    }

    /**
     * 综合搜索，根据关键字和分类查询 ES商品
     *
     * @param keyword 关键字， 必须
     * @param categoryId 分类ID，可选
     * @param sort 排序规则，参见 {@link SortEnum}, 可选
     * @param pageNum 当前页码， 可选，默认 0
     * @param pageSize 每页限定的，可选，默认 5，最大不超过 20
     * @return Es商品列表
     */
    @PostMapping(value = "/total/search")
    public Response<Object> commonSearch(@RequestParam("keyword") String keyword,
                                         @RequestParam(value = "productCategoryId",required = false) Long categoryId,
                                         @RequestParam(value = "sort",required = false) Integer sort,
                                         @RequestParam(value = "page",defaultValue = "0",required = false) Integer pageNum,
                                         @RequestParam(value = "size",defaultValue = "5",required = false) Integer pageSize,
                                         @Jwt JwtUser user){
        if (categoryId != null && categoryId < 0){
            return Response.fail(ResponseStatus.CATEGORY_ID_WRONG);
        }
        if (sort != null && SortEnum.getSort(sort) == null){
            return Response.fail(ResponseStatus.SORT_WRONG);
        }
        if (pageNum < 0 || pageSize > 20){
            return Response.fail(ResponseStatus.PAGINATION_WRONG);
        }

        Page<EsProduct> esProducts = esProductService.search(keyword, categoryId, pageNum, pageSize, sort);
        searchKeywordCacheManager.cacheGoodsSearchKeyword(user.getOpenId(),keyword);
        LOGGER.info("openId={} 的用户查询商品信息，keyword={}", user.getOpenId(),keyword);
        return Response.ok(new SearchGoodsPageVO(esProducts));
    }

    /**
     * 清空搜索历史
     *
     */
    @GetMapping("/clearhistory")
    public Response<Object> clearHistorySearch(@Jwt JwtUser jwtUser){
        searchKeywordCacheManager.clearSearchHistory(jwtUser.getOpenId());
        LOGGER.info("用户openId= 【{}】清空搜索历史", jwtUser.getOpenId());
        return Response.ok(null);
    }

    /**
     * 搜索页,展示热门关键字和当前用户的搜索历史
     *
     * @param jwtUser JwtUser
     * @return SearchPageVo
     */
    @GetMapping("/index")
    public Response<SearchPageVo> searchIndex(@Jwt JwtUser jwtUser) {
        Set<String> historyKeyword = new TreeSet<>();
        String openId = null;
        if (jwtUser != null) {
            openId = jwtUser.getOpenId();
            historyKeyword = searchKeywordCacheManager.getUserSearchHistory(openId, 10);
        }
        Set<String> hotSearch = searchKeywordCacheManager.getHot(0, 9);
        SearchPageVo vo = new SearchPageVo(new ArrayList<>(historyKeyword), new ArrayList<>(hotSearch));
        LOGGER.info("用户openId= 【{}】获取搜索历史和热门关键词，搜索历史 = 【{}】，热门关键词 = 【{}】", openId, historyKeyword, hotSearch);
        return Response.ok(vo);
    }
}