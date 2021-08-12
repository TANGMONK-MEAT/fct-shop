package io.github.tangmonkmeat.service.impl;

import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.model.po.PostExample;
import io.github.tangmonkmeat.exception.BusinessException;
import io.github.tangmonkmeat.mapper.*;
import io.github.tangmonkmeat.model.*;
import io.github.tangmonkmeat.service.GoodsPublishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 下午4:39
 */
@Service
public class GoodsPublishServiceImpl implements GoodsPublishService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsGalleryMapper goodsGalleryMapper;

    @Resource
    private RegionMapper regionMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long postGoods(PostExample post) {
        List<String> images = post.getImages();
        post.setPrimaryPicUrl(images.get(0));

        // 添加商品并获取id
        goodsMapper.insertSelective(post);

        final long goodsId = post.getId();

        // 保存商品图片
        List<GoodsGallery> goodsGalleries = new ArrayList<>(images.size());
        images.forEach(uri -> {
            GoodsGallery goodsGallery = new GoodsGallery();
            goodsGallery.setGoodsId(goodsId);
            goodsGallery.setImgUrl(uri);
            goodsGalleries.add(goodsGallery);
        });
        goodsGalleryMapper.insertBatch(goodsGalleries);
        return goodsId;
    }

    @Override
    public int deleteGoods(int goodsId, String userId) throws Exception {
        if (goodsMapper.validateSeller(goodsId,userId)){
            return goodsMapper.deleteByPrimaryKey(goodsId);
        }else {
            throw new BusinessException(ResponseStatus.SELLER_AND_GOODS_IS_NOT_MATCH.errno(),
                    ResponseStatus.SELLER_AND_GOODS_IS_NOT_MATCH.errmsg());
        }
    }

    @Override
    public List<Region> getRegionList(int regionId) {
        RegionExample regionExample = new RegionExample();
        regionExample.createCriteria().andParentIdEqualTo((short)regionId);
        return regionMapper.selectByExample(regionExample);
    }

    @Override
    public List<Category> getCateList(int cateId) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andParentIdEqualTo(cateId);
        return categoryMapper.selectByExample(categoryExample);
    }
}
