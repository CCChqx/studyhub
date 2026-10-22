package com.studyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyhub.common.ErrorCode;
import com.studyhub.common.PageResult;
import com.studyhub.converter.QuestionConverter;
import com.studyhub.exception.BusinessException;
import com.studyhub.mapper.CategoryMapper;
import com.studyhub.mapper.KnowledgePointMapper;
import com.studyhub.mapper.QuestionMapper;
import com.studyhub.pojo.dto.QuestionCreateDTO;
import com.studyhub.pojo.dto.QuestionUpdateDTO;
import com.studyhub.pojo.entity.Category;
import com.studyhub.pojo.entity.KnowledgePoint;
import com.studyhub.pojo.entity.Question;
import com.studyhub.pojo.query.QuestionQuery;
import com.studyhub.pojo.vo.QuestionVO;
import com.studyhub.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    private final CategoryMapper categoryMapper;
    private final KnowledgePointMapper knowledgePointMapper;

    @Override
    public PageResult<QuestionVO> pageQuestions(QuestionQuery query) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getSubjectId() != null, Question::getSubjectId, query.getSubjectId());
        wrapper.eq(query.getCategoryId() != null, Question::getCategoryId, query.getCategoryId());
        wrapper.eq(query.getKpId() != null, Question::getKpId, query.getKpId());
        wrapper.eq(query.getType() != null, Question::getType, query.getType());
        wrapper.eq(query.getStatus() != null, Question::getStatus, query.getStatus());
        wrapper.like(StringUtils.hasText(query.getKeyword()), Question::getStem, query.getKeyword());
        wrapper.orderByDesc(Question::getId);

        this.page(query, wrapper);

        List<QuestionVO> voList = query.getRecords().stream()
                .map(QuestionConverter::toVO)
                .toList();
        return PageResult.of(query.getTotal(), voList);
    }

    @Override
    public QuestionVO getQuestionById(Long id) {
        Question question = this.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return QuestionConverter.toVO(question);
    }

    @Override
    @Transactional
    public QuestionVO createQuestion(QuestionCreateDTO dto) {
        validateRelations(dto.getSubjectId(), dto.getCategoryId(), dto.getKpId());
        validateContent(dto.getType(), dto.getOptions());

        Question question = QuestionConverter.toEntity(dto);
        this.save(question);
        return QuestionConverter.toVO(question);
    }

    @Override
    @Transactional
    public QuestionVO updateQuestion(QuestionUpdateDTO dto) {
        Question question = this.getById(dto.getId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }

        validateRelations(dto.getSubjectId(), dto.getCategoryId(), dto.getKpId());
        validateContent(dto.getType(), dto.getOptions());

        question.setSubjectId(dto.getSubjectId());
        question.setCategoryId(dto.getCategoryId());
        question.setKpId(dto.getKpId());
        question.setType(dto.getType());
        question.setStem(dto.getStem());
        question.setOptions(dto.getOptions());
        question.setAnswer(dto.getAnswer());
        question.setAnalysis(dto.getAnalysis());
        if (dto.getStatus() != null) {
            question.setStatus(dto.getStatus());
        }

        this.updateById(question);
        return QuestionConverter.toVO(question);
    }

    @Override
    @Transactional
    public void deleteQuestionById(Long id) {
        if (!this.removeById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }

    /**
     * 校验题目挂在正确的内容层级下，防止跨学科/跨分类脏数据。
     */
    private void validateRelations(Long subjectId, Long categoryId, Long kpId) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "分类不存在");
        }
        if (!category.getSubjectId().equals(subjectId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分类不属于该学科");
        }

        if (kpId != null) {
            KnowledgePoint kp = knowledgePointMapper.selectById(kpId);
            if (kp == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND, "知识点不存在");
            }
            if (!kp.getCategoryId().equals(categoryId) || !kp.getSubjectId().equals(subjectId)) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "知识点不属于该分类或学科");
            }
        }
    }

    /**
     * 选择题必须有选项；题型范围由 DTO 校验，这里再做一次兜底。
     */
    private void validateContent(Integer type, String options) {
        if (type == null || type < 1 || type > 3) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "题目类型只能是 1/2/3");
        }
        if ((type == 1 || type == 2) && !StringUtils.hasText(options)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "选择题选项不能为空");
        }
    }
}
