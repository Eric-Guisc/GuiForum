package priv.gsc.guiforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.gsc.guiforum.dao.CategoryMapper;
import priv.gsc.guiforum.entity.Category;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> findCategories() {
        List<Category> categories = categoryMapper.selectCategories();
        return categories;
    }

    public Category findCategoryById(int id) {
        Category category = categoryMapper.selectCategoryById(id);
        return category;
    }
}
