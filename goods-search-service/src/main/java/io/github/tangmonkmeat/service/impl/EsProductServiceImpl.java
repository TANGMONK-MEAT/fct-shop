package io.github.tangmonkmeat.service.impl;

import io.github.tangmonkmeat.mapper.CategoryMapper;
import io.github.tangmonkmeat.mapper.EsProductMapper;
import io.github.tangmonkmeat.model.CategoryMenu;
import io.github.tangmonkmeat.model.EsProduct;
import io.github.tangmonkmeat.model.SortEnum;
import io.github.tangmonkmeat.repository.EsProductRepository;
import io.github.tangmonkmeat.service.EsProductService;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 商品Es查询业务操作
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/18 下午5:10
 */
@Service
public class EsProductServiceImpl implements EsProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(EsProductServiceImpl.class);

    @Resource
    private EsProductMapper esProductMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Autowired
    private EsProductRepository esProductRepository;

    @Override
    public int importAll() {
        List<EsProduct> productList = esProductMapper.getAllEsProductList(null, null);
        esProductRepository.saveAll(productList);
        return productList.size();
    }

    @Override
    public void delete(Long id) {
        esProductRepository.deleteById(id.intValue());
    }

    @Override
    public EsProduct create(Long id) {
        return esProductMapper.getEsProductByPrimaryId(id.intValue());
    }

    @Override
    public void delete(List<Long> ids) {
        ArrayList<EsProduct> products = new ArrayList<>(ids.size());
        for(Long id : ids){
            EsProduct esProduct = new EsProduct();
            esProduct.setId(id.intValue());
            products.add(esProduct);
        }
        esProductRepository.deleteAll(products);
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esProductRepository.findByNameOrRegionOrDesc(keyword, keyword, keyword, pageable);
    }

    @Override
    public Page<EsProduct> search(String keyword, Long categoryId, Integer pageNum, Integer pageSize, Integer sort) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        // 查询策略
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 分页
        queryBuilder.withPageable(pageable);
        // 过滤
        if (categoryId != null){
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            boolQuery.must(QueryBuilders.termQuery("categoryId",categoryId));
            queryBuilder.withFilter(boolQuery);
        }
        // 搜素
        if (StringUtils.isEmpty(keyword)){
            // 全局匹配
            queryBuilder.withQuery(QueryBuilders.matchAllQuery());
        }else {
            ArrayList<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("name", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("desc", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(5)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder
                    .FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2);
            queryBuilder.withQuery(functionScoreQueryBuilder);
        }

        // 排序
        if (sort == SortEnum.NEW_TO_OLD.getType()){
            // 按新品到旧
            queryBuilder.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC));
        }else if(sort == SortEnum.PRICE_DESC.getType()){
            // 价格从低到高
            queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        } else if(sort == SortEnum.PRICE_ASC.getType()){
            // 价格从高到低
            queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        } else {
            // 按相关性
            queryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        }

        queryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        NativeSearchQuery searchQuery = queryBuilder.build();
        LOGGER.info("DSL: {}",searchQuery.getQuery().toString());
        return esProductRepository.search(searchQuery);
    }

    @Override
    public Page<EsProduct> recommend(Long id, Integer pageNum, Integer pageSize) {
        EsProduct product = esProductMapper.getEsProductByPrimaryId(id.intValue());
        if (!ObjectUtils.isEmpty(product)){
            Integer id1 = product.getId();
            String keyword = product.getName();
            String desc = product.getDesc();
            Integer categoryId = product.getCategoryId();
            CategoryMenu categoryMenus = categoryMapper.selectParentCategoryMenu(id1);

            // 根据商品标题，分类进行搜索
            ArrayList<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("name", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("desc", desc),
                    ScoreFunctionBuilders.weightFactorFunction(8)));
            while (categoryMenus != null){
                filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        QueryBuilders.matchQuery("categoryName", categoryMenus.getName()),
                        ScoreFunctionBuilders.weightFactorFunction(5)));
                categoryMenus = categoryMenus.getParent();
            }

            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("categoryId", categoryId),
                    ScoreFunctionBuilders.weightFactorFunction(2)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder
                    .FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2);

            // 过滤相同的商品
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            boolQueryBuilder.mustNot(QueryBuilders.termQuery("id",id));

            Pageable pageable = PageRequest.of(pageNum, pageSize);
            // 构建查询条件
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            queryBuilder.withQuery(functionScoreQueryBuilder);
            queryBuilder.withFilter(boolQueryBuilder);
            queryBuilder.withPageable(pageable);
            NativeSearchQuery searchQuery = queryBuilder.build();
            LOGGER.info("DSL: {}",searchQuery);

            Page<EsProduct> result = esProductRepository.search(searchQuery);
            if (result.getContent().size() == 0){
                queryBuilder.withQuery(QueryBuilders.matchAllQuery());
                result = esProductRepository.search(searchQuery);
            }

            return result;
        }
        return new PageImpl<>(new ArrayList<>());
    }
}
