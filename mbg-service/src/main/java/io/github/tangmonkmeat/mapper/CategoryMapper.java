package io.github.tangmonkmeat.mapper;

import io.github.tangmonkmeat.model.Category;
import io.github.tangmonkmeat.model.CategoryExample;
import java.util.List;

import io.github.tangmonkmeat.model.CategoryMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper {
    List<Category> selectAllMainCategory();

    List<Category> selectBrotherCategory(@Param("id") int id);

    List<Category> selectAllSubcategory(int parentId);

    int selectParentIdById(@Param("id") int id);

    long countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    CategoryMenu selectParentCategoryMenu(Integer id);
}