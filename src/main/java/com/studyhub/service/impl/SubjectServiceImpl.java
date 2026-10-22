package com.studyhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyhub.common.ErrorCode;
import com.studyhub.converter.SubjectConverter;
import com.studyhub.exception.BusinessException;
import com.studyhub.mapper.CategoryMapper;
import com.studyhub.mapper.KnowledgePointMapper;
import com.studyhub.mapper.QuestionMapper;
import com.studyhub.mapper.SubjectMapper;
import com.studyhub.pojo.dto.SubjectCreateDTO;
import com.studyhub.pojo.dto.SubjectUpdateDTO;
import com.studyhub.pojo.entity.Category;
import com.studyhub.pojo.entity.KnowledgePoint;
import com.studyhub.pojo.entity.Question;
import com.studyhub.pojo.entity.Subject;
import com.studyhub.pojo.vo.SubjectVO;
import com.studyhub.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    private final KnowledgePointMapper knowledgePointMapper;
    private final QuestionMapper questionMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public SubjectVO createSubject(SubjectCreateDTO dto) {
        Subject subject = SubjectConverter.toEntity(dto);
        this.save(subject);
        return SubjectConverter.toVO(subject);
    }

    @Override
    public List<SubjectVO> listSubjects() {
        List<Subject> list = this.list();
        return list.stream()
                .map(SubjectConverter::toVO)
                .toList();
    }

    @Override
    public SubjectVO getSubjectById(Long id) {
        Subject subject = this.getById(id);
        if (subject == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return SubjectConverter.toVO(subject);
    }

    @Override
    public SubjectVO updateSubject(SubjectUpdateDTO dto) {
        Subject subject = this.getById(dto.getId());
        if (subject == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        subject.setName(dto.getName());
        subject.setCode(dto.getCode());
        subject.setDescription(dto.getDescription());
        this.updateById(subject);
        return SubjectConverter.toVO(subject);
    }

    @Override
    @Transactional
    public void deleteSubjectById(Long id) {
        // 1. 学科下是否有所属章节
        long categoryCount = categoryMapper.selectCount(new LambdaQueryWrapper<Category>()
                .eq(Category::getSubjectId,id));
        if (categoryCount > 0) {
            throw new BusinessException(ErrorCode.SUBJECT_HAS_CONTENT);
        }
        // 2. 学科下是否有所属知识点
        long kpCount = knowledgePointMapper.selectCount(new LambdaQueryWrapper<KnowledgePoint>()
                .eq(KnowledgePoint::getSubjectId,id));
        if (kpCount > 0) {
            throw new BusinessException(ErrorCode.SUBJECT_HAS_CONTENT);
        }
        // 3. 学科下是否有所属问题
        long questionCount = questionMapper.selectCount(new LambdaQueryWrapper<Question>()
                .eq(Question::getSubjectId,id));
        if (questionCount > 0) {
            throw new BusinessException(ErrorCode.SUBJECT_HAS_CONTENT);
        }

        if (! this.removeById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }
}
