package com.studyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyhub.common.ErrorCode;
import com.studyhub.converter.CategoryConverter;
import com.studyhub.exception.BusinessException;
import com.studyhub.mapper.CategoryMapper;
import com.studyhub.mapper.KnowledgePointMapper;
import com.studyhub.mapper.QuestionMapper;
import com.studyhub.pojo.dto.CategoryCreateDTO;
import com.studyhub.pojo.dto.CategoryUpdateDTO;
import com.studyhub.pojo.entity.Category;
import com.studyhub.pojo.entity.KnowledgePoint;
import com.studyhub.pojo.entity.Question;
import com.studyhub.pojo.vo.CategoryVO;
import com.studyhub.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService{

    private final KnowledgePointMapper knowledgePointMapper;
    private final QuestionMapper questionMapper;

    @Override
    public CategoryVO getCategoryById(Long id) {
        Category category = this.getById(id);
        if (category == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return CategoryConverter.toVO(category);
    }

    @Override
    public List<CategoryVO> listCategories(Long subjectId) {
        List<Category> list = this.list(new LambdaQueryWrapper<Category>()
                .eq(Category::getSubjectId, subjectId)
                .orderByAsc(Category::getSort));
        return list.stream()
                .map(CategoryConverter::toVO)
                .toList();
    }

    @Override
    public CategoryVO createCategory(CategoryCreateDTO dto) {
        Category category = CategoryConverter.toEntity(dto);
        this.save(category);
        return CategoryConverter.toVO(category);
    }

    @Override
    public CategoryVO updateCategory(CategoryUpdateDTO dto) {
        Category category = this.getById(dto.getId());
        if (category == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        category.setId(dto.getId());
        category.setParentId(dto.getParentId());
        category.setName(dto.getName());
        category.setSort(dto.getSort());
        this.updateById(category);
        return CategoryConverter.toVO(category);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        Category category = this.getById(id);
        if (category == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }

        // 1. 子分类 : Category 有 @TableLogic this.count 会自动加 deleted = 0
        long childCount = this.count(new LambdaQueryWrapper<Category>()
                .eq(Category::getParentId,id));
        if (childCount > 0) {
            throw new BusinessException(ErrorCode.CATEGORY_HAS_CHILDREN);
        }

        // 2. 知识点 : KnowledgePoint
        long kpCount = knowledgePointMapper.selectCount(new LambdaQueryWrapper<KnowledgePoint>()
                .eq(KnowledgePoint::getCategoryId,id));
        if (kpCount > 0) {
            throw new BusinessException(ErrorCode.CATEGORY_HAS_CONTENT);
        }

        // 3. 题目 : Question
        long questionCount = questionMapper.selectCount(new LambdaQueryWrapper<Question>()
                .eq(Question::getCategoryId,id));
        if (questionCount > 0) {
            throw new BusinessException(ErrorCode.CATEGORY_HAS_CONTENT);
        }

        if (! this.removeById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }
}
