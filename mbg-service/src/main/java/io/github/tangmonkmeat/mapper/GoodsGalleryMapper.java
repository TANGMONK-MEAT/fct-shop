package io.github.tangmonkmeat.mapper;

import io.github.tangmonkmeat.model.GoodsGallery;
import io.github.tangmonkmeat.model.GoodsGalleryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsGalleryMapper {
    long countByExample(GoodsGalleryExample example);

    int deleteByExample(GoodsGalleryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsGallery record);

    int insertBatch(List<GoodsGallery> records);

    int insertSelective(GoodsGallery record);

    List<GoodsGallery> selectByExample(GoodsGalleryExample example);

    GoodsGallery selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsGallery record, @Param("example") GoodsGalleryExample example);

    int updateByExample(@Param("record") GoodsGallery record, @Param("example") GoodsGalleryExample example);

    int updateByPrimaryKeySelective(GoodsGallery record);

    int updateByPrimaryKey(GoodsGallery record);
}