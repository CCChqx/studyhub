package com.studyhub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyhub.common.ErrorCode;
import com.studyhub.converter.SubjectConverter;
import com.studyhub.pojo.dto.SubjectCreateDTO;
import com.studyhub.pojo.dto.SubjectUpdateDTO;
import com.studyhub.pojo.entity.Subject;
import com.studyhub.exception.BusinessException;
import com.studyhub.mapper.SubjectMapper;
import com.studyhub.service.SubjectService;
import com.studyhub.pojo.vo.SubjectVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

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
    public void deleteSubjectById(Long id) {
        if (!this.removeById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }
}
