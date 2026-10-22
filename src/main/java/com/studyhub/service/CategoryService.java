package com.studyhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyhub.pojo.dto.CategoryCreateDTO;
import com.studyhub.pojo.dto.CategoryUpdateDTO;
import com.studyhub.pojo.entity.Category;
import com.studyhub.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryService extends IService<Category> {

    CategoryVO getCategoryById(Long id);

    List<CategoryVO> listCategories(Long subjectId);

    CategoryVO createCategory(CategoryCreateDTO dto);

    CategoryVO updateCategory(CategoryUpdateDTO dto);

    void deleteCategoryById(Long id);
}
