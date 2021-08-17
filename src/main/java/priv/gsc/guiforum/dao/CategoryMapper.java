package priv.gsc.guiforum.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.gsc.guiforum.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    // 新增分类
    int insertCategory(Category category);

    // 查询所有分类
    List<Category> selectCategories();

    // 根据ID查询分类
    Category selectCategoryById(int id);
}
