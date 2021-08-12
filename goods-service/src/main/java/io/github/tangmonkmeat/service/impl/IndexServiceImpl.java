package io.github.tangmonkmeat.service.impl;

import com.github.pagehelper.PageHelper;
import io.github.tangmonkmeat.constant.IndexConstant;
import io.github.tangmonkmeat.mapper.CategoryMapper;
import io.github.tangmonkmeat.mapper.GoodsMapper;
import io.github.tangmonkmeat.mapper.IndexMapper;
import io.github.tangmonkmeat.model.Ad;
import io.github.tangmonkmeat.model.Category;
import io.github.tangmonkmeat.model.Channel;
import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.model.vo.CatalogPageVo;
import io.github.tangmonkmeat.model.vo.IndexPageVo;
import io.github.tangmonkmeat.service.IndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/22 上午10:28
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private IndexMapper indexMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Ad> getAdList() {
        return indexMapper.findAd(IndexConstant.AD_COUNT);
    }

    @Override
    public List<Channel> getChannelList() {
        return indexMapper.findChannel(IndexConstant.CHANNEL_COUNT);
    }

    @Override
    public IndexPageVo getIndex(int page, int size) {
        List<Ad> ads = getAdList();
        List<Channel> channels = getChannelList();
        List<Goods> goodsList = getIndexMore(page,size);
        return new IndexPageVo(goodsList,ads,channels);
    }

    @Override
    public List<Goods> getIndexMore(int page, int size) {
        PageHelper.startPage(page,size);
        return goodsMapper.selectSimpleGoods();
    }

    @Override
    public CatalogPageVo getCatalogIndex() {
        // 所有主分类
        List<Category> mainCategory = categoryMapper.selectAllMainCategory();
        // 第一主分类的子分类
        Category category = mainCategory.get(0);
        List<Category> subcategory = categoryMapper.selectAllSubcategory(category.getId());
        return new CatalogPageVo(mainCategory,subcategory);
    }

    @Override
    public List<Category> getSubCatalogById(int id) {
        return categoryMapper.selectAllSubcategory(id);
    }
}
