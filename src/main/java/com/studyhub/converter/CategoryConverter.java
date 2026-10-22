package com.studyhub.converter;

import com.studyhub.pojo.dto.CategoryCreateDTO;
import com.studyhub.pojo.entity.Category;
import com.studyhub.pojo.vo.CategoryVO;

/**
 * Category entity <-> VO 转换。
 */
public final class CategoryConverter {

    private CategoryConverter() {
    }

    public static CategoryVO toVO(Category entity) {
        return CategoryVO.builder()
                .id(entity.getId())
                .subjectId(entity.getSubjectId())
                .parentId(entity.getParentId())
                .name(entity.getName())
                .sort(entity.getSort())
                .build();
    }

    public static Category toEntity(CategoryCreateDTO dto) {
        Category category = new Category();
        category.setSubjectId(dto.getSubjectId());
        category.setParentId(dto.getParentId());
        category.setName(dto.getName());
        category.setSort(dto.getSort());
        return category;
    }
}
